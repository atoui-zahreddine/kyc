import { useEffect, useState } from 'react';
import axios from 'app/config/axiosPublicInstance';

export interface DilisenseApiResponse {
  found_records: any[];
  fuzzySearch: boolean;
  total_hits: number;
}

const useDilisenseSanctionApi = (query = '', revision = 0) => {
  const [result, setResult] = useState<DilisenseApiResponse>();
  const [error, setError] = useState<string>();
  const [loading, setLoading] = useState<boolean>(false);

  const getResultWithPayedApi = async () => {
    try {
      const { data } = await axios.get(
        `https://api.dilisense.com/v1/checkIndividual?names=${query}`,

        { headers: { 'X-API-Key': 'E4tvrFDgCXMQlyGzY0an3MXxw3HTDA987sasq5J6' } }
      );
      setResult(data);
    } catch (e) {
      console.error(e);
      setError(e);
    } finally {
      setLoading(false);
    }
  };

  const getResultWithNotPayedApi = async () => {
    try {
      const { data } = await axios.post(`https://site-api.dilisense.com/website/search`, {
        query: 'slim riahi',
        offset: 0,
        sourceType: 'sanction',
        firstTimeVisitor: false,
      });
      setResult(data);
    } catch (e) {
      console.error(e);
      setError(e);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (query) {
      setLoading(true);
      getResultWithPayedApi().catch(console.error);
      // getResultWithNotPayedApi().catch(console.error);
    }
  }, [query, revision]);
  return { result, error, loading };
};

export default useDilisenseSanctionApi;
