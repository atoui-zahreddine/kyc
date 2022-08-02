import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ApplicantLevel from './applicant-level';
import ApplicantLevelDetail from './applicant-level-detail';
import ApplicantLevelUpdate from './applicant-level-update';
import ApplicantLevelDeleteDialog from './applicant-level-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ApplicantLevelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ApplicantLevelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ApplicantLevelDetail} />
      <ErrorBoundaryRoute path={match.url} component={ApplicantLevel} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ApplicantLevelDeleteDialog} />
  </>
);

export default Routes;
