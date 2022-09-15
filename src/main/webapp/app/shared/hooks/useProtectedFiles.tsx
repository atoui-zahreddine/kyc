import { useEffect, useState } from 'react';
import axios from 'axios';
import { Buffer } from 'buffer';

const useProtectedFiles = (imageUrl: string) => {
  const [fileBase64, setFileBase64] = useState<string>('');
  useEffect(() => {
    axios
      .get(`/api/files/${imageUrl}`, { responseType: 'arraybuffer' })
      .then(({ data, headers }) => {
        const base64 = Buffer.from(data, 'binary').toString('base64');
        const file = 'data:' + headers['content-type'] + ';base64,' + base64;
        setFileBase64(file);
      })
      .catch(console.error);
  }, [imageUrl]);
  return { fileBase64 };
};

export default useProtectedFiles;
