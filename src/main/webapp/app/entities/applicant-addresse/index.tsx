import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ApplicantAddresse from './applicant-addresse';
import ApplicantAddresseDetail from './applicant-addresse-detail';
import ApplicantAddresseUpdate from './applicant-addresse-update';
import ApplicantAddresseDeleteDialog from './applicant-addresse-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ApplicantAddresseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ApplicantAddresseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ApplicantAddresseDetail} />
      <ErrorBoundaryRoute path={match.url} component={ApplicantAddresse} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ApplicantAddresseDeleteDialog} />
  </>
);

export default Routes;
