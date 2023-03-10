import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import PageNotFound from 'app/shared/error/page-not-found';
import ApplicantLevel from './applicant-level';
import Applicants from './applicants';

const DashboardRoutes = ({ match }) => {
  return (
    <div className="view-routes">
      <Switch>
        <ErrorBoundaryRoute path={`${match.url}/applicants`} component={Applicants} />
        <ErrorBoundaryRoute path={`${match.url}/applicant-levels`} component={ApplicantLevel} />
        <ErrorBoundaryRoute component={PageNotFound} />
      </Switch>
    </div>
  );
};

export default DashboardRoutes;
