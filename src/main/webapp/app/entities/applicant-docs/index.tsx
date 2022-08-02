import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ApplicantDocs from './applicant-docs';
import ApplicantDocsDetail from './applicant-docs-detail';
import ApplicantDocsUpdate from './applicant-docs-update';
import ApplicantDocsDeleteDialog from './applicant-docs-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ApplicantDocsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ApplicantDocsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ApplicantDocsDetail} />
      <ErrorBoundaryRoute path={match.url} component={ApplicantDocs} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ApplicantDocsDeleteDialog} />
  </>
);

export default Routes;
