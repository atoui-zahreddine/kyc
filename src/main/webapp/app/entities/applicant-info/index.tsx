import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ApplicantInfo from './applicant-info';
import ApplicantInfoDetail from './applicant-info-detail';
import ApplicantInfoUpdate from './applicant-info-update';
import ApplicantInfoDeleteDialog from './applicant-info-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ApplicantInfoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ApplicantInfoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ApplicantInfoDetail} />
      <ErrorBoundaryRoute path={match.url} component={ApplicantInfo} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ApplicantInfoDeleteDialog} />
  </>
);

export default Routes;
