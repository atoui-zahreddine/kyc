import React from 'react';
import { Switch } from 'react-router-dom';

import Login from 'app/modules/login/login';
import Logout from 'app/modules/login/logout';
import Home from 'app/modules/home/home';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import PageNotFound from 'app/shared/error/page-not-found';
import Dashboard from 'app/modules/dashboard';

const Routes = () => {
  return (
    <div className="view-routes">
      <Switch>
        <ErrorBoundaryRoute path="/" exact component={Home} />
        <ErrorBoundaryRoute path="/login" component={Login} />
        <ErrorBoundaryRoute path="/logout" component={Logout} />
        <ErrorBoundaryRoute path="/dashboard" component={Dashboard} />
        <ErrorBoundaryRoute component={PageNotFound} />
      </Switch>
    </div>
  );
};

export default Routes;
