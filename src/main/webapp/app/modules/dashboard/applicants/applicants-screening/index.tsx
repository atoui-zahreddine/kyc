import React, { useEffect } from 'react';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity } from 'app/entities/applicant-info/applicant-info.reducer';
import ApplicantInfo from './components/applicant-info';
import RiskLevel from './components/risk-level';
import Checks from './components/checks';
import WatchList from './components/watchlist';
import IdentityDocuments from './components/IdentityDocuments';
import Liveness from './components/Liveness';

import './styles.scss';

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
      <IdentityDocuments />
      <Liveness />
    </div>
  );
};

export default ApplicantsScreening;
