import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserAgentInfo from './user-agent-info';
import UserAgentInfoDetail from './user-agent-info-detail';
import UserAgentInfoUpdate from './user-agent-info-update';
import UserAgentInfoDeleteDialog from './user-agent-info-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserAgentInfoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserAgentInfoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserAgentInfoDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserAgentInfo} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={UserAgentInfoDeleteDialog} />
  </>
);

export default Routes;
