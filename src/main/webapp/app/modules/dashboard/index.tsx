import React from 'react';
import DashboardLayout from 'app/shared/layout/dashboard-layout';

import DashboardRoutes from './dashboard-routes';

const Dashboard = ({ match }) => {
  return (
    <div>
      <DashboardLayout>
        <DashboardRoutes match={match} />
      </DashboardLayout>
    </div>
  );
};

export default Dashboard;
