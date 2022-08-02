import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import IpInfo from './ip-info';
import IpInfoDetail from './ip-info-detail';
import IpInfoUpdate from './ip-info-update';
import IpInfoDeleteDialog from './ip-info-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={IpInfoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={IpInfoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={IpInfoDetail} />
      <ErrorBoundaryRoute path={match.url} component={IpInfo} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={IpInfoDeleteDialog} />
  </>
);

export default Routes;
