import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import NewApplicant from 'app/modules/dashboard/applicants/applicants-new';
import ApplicantsScreening from 'app/modules/dashboard/applicants/applicants-screening';

const Applicant = ({ match }) => {
  return (
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NewApplicant} />
      <ErrorBoundaryRoute exact path={`${match.url}/screening/:id`} component={ApplicantsScreening} />
    </Switch>
  );
};

export default Applicant;
