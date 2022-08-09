import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import ApplicantLevelList from './applicant-level-list/';

function ApplicantLevel({ match }) {
  return (
    <Switch>
      <ErrorBoundaryRoute path={match.url} component={ApplicantLevelList} />
    </Switch>
  );
}

export default ApplicantLevel;
