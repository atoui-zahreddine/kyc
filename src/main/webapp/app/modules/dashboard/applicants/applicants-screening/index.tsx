import React, { useEffect, useState } from 'react';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity } from 'app/entities/applicant-info/applicant-info.reducer';
import ApplicantInfo from './components/applicant-info';
import RiskLevel from './components/risk-level';
import Checks from './components/checks';
import WatchList from './components/watchlist';
import IdentityDocuments from './components/IdentityDocuments';
import Liveness from './components/Liveness';

import './styles.scss';
import Sanctions from './components/sanctions';
import useDilisenseSanctionApi from 'app/shared/hooks/useDilisenseSanctionApi';

const watchListData = [
  {
    name: 'Adverse Media.',
    warnings: 'CLEAN',
    status: 'DONE',
    searchDate: 'Jul 19, 2022, 10:44:28 AM',
  },
  {
    name: 'Sanction.',
    warnings: 'CLEAN',
    status: 'DONE',
    searchDate: 'Jul 19, 2022, 10:44:28 AM',
  },
  {
    name: 'Warning.',
    warnings: '2 WARNINGS',
    status: 'DONE',
    searchDate: 'Jul 19, 2022, 10:44:28 AM',
  },
  {
    name: 'Fitness Probity.',
    warnings: 'CLEAN',
    status: 'DONE',
    searchDate: 'Jul 19, 2022, 10:44:28 AM',
  },
];

const checksData = [
  {
    name: 'Drivers License',
    warnings: '2 WARNINGS',
    searchDate: 'Jul 19, 2022, 10:44:28 AM',
  },
  {
    name: 'Liveness',
    warnings: 'CLEAN',
    searchDate: 'Jul 19, 2022, 10:44:28 AM',
  },
  {
    name: 'WatchList',
    warnings: 'CLEAN',
    searchDate: 'Jul 19, 2022, 10:44:28 AM',
  },
];

const ApplicantsScreening = ({ match }) => {
  const { entity: applicantInfo } = useAppSelector(state => state.applicantInfo);
  const [revision, setRevision] = useState(0);
  const query = applicantInfo.firstName ? `${applicantInfo.firstName} ${applicantInfo.lastName ?? ''}` : '';
  const { result, loading } = useDilisenseSanctionApi(query, revision);
  result?.found_records?.length !== 0 ? (watchListData[1].warnings = 'SUSPICIOUS') : (watchListData[1].warnings = 'CLEAN');

  const rerunCheck = () => {
    setRevision(prevRevision => prevRevision + 1);
  };
  const dispatch = useAppDispatch();

  useEffect(() => {
    const applicantId = match.params.id;
    if (applicantId) dispatch(getEntity(applicantId));
  }, [match.params.id]);

  return (
    <div className="level-result">
      <ApplicantInfo applicantInfo={applicantInfo} />
      <RiskLevel />
      <Checks checksData={checksData} />
      <WatchList watchListData={watchListData} />
      <Sanctions result={result} loading={loading} rerunCheck={rerunCheck} />
      <IdentityDocuments applicantDocs={applicantInfo.applicantDocs} />
      <Liveness applicantDocs={applicantInfo.applicantDocs} />
    </div>
  );
};

export default ApplicantsScreening;
