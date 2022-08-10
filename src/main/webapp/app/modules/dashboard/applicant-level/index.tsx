import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ApplicantLevelList from './applicant-level-list/';
import ApplicantLevelUpdate from 'app/entities/applicant-level/applicant-level-update';
import ApplicantLevelDetail from 'app/entities/applicant-level/applicant-level-detail';
import NewApplicantLevel from 'app/modules/dashboard/applicant-level/applicant-level-new';

function ApplicantLevel({ match }) {
  return (
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NewApplicantLevel} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ApplicantLevelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ApplicantLevelDetail} />
      <ErrorBoundaryRoute path={match.url} component={ApplicantLevelList} />
    </Switch>
  );
}

export default ApplicantLevel;
