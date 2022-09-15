import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import NewApplicant from 'app/modules/dashboard/applicants/applicants-new';
import ApplicantsScreening from 'app/modules/dashboard/applicants/applicants-screening';
import ApplicantsList from 'app/modules/dashboard/applicants/applicants-list';

const Applicant = ({ match }) => {
  return (
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NewApplicant} />
      <ErrorBoundaryRoute exact path={`${match.url}/details/:id`} component={ApplicantsScreening} />
      <ErrorBoundaryRoute path={match.url} component={ApplicantsList} />
    </Switch>
  );
};

export default Applicant;
