import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DocSet from './doc-set';
import DocSetDetail from './doc-set-detail';
import DocSetUpdate from './doc-set-update';
import DocSetDeleteDialog from './doc-set-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DocSetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DocSetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DocSetDetail} />
      <ErrorBoundaryRoute path={match.url} component={DocSet} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DocSetDeleteDialog} />
  </>
);

export default Routes;
