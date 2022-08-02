import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ApplicantPhone from './applicant-phone';
import ApplicantPhoneDetail from './applicant-phone-detail';
import ApplicantPhoneUpdate from './applicant-phone-update';
import ApplicantPhoneDeleteDialog from './applicant-phone-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ApplicantPhoneUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ApplicantPhoneUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ApplicantPhoneDetail} />
      <ErrorBoundaryRoute path={match.url} component={ApplicantPhone} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ApplicantPhoneDeleteDialog} />
  </>
);

export default Routes;
