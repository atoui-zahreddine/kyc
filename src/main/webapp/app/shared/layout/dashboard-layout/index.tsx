import React from 'react';
import { Stack } from '@fluentui/react';
import './dashboard-layout.scss';
import Nav from 'app/shared/layout/dashboard-layout/nav';

const DashboardLayout = ({ children }) => {
  return (
    <Stack horizontal tokens={{ childrenGap: 32, padding: '0 1rem 0 0' }}>
      <Nav />
      <div className="dashboard-main">{children}</div>
    </Stack>
  );
};

export default DashboardLayout;
