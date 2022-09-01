import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ApplicantLevelList from './applicant-level-list/';
import NewApplicantLevel from 'app/modules/dashboard/applicant-level/applicant-level-new';

function ApplicantLevel({ match }) {
  return (
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NewApplicantLevel} />
      <ErrorBoundaryRoute exact path={`${match.url}/details/:id`} component={NewApplicantLevel} />
      <ErrorBoundaryRoute path={match.url} component={ApplicantLevelList} />
    </Switch>
  );
}

export default ApplicantLevel;
