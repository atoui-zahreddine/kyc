import React from 'react';
import { Stack } from '@fluentui/react';
import './dashboard-layout.scss';
import Nav from 'app/shared/layout/dashboard-layout/nav';

const DashboardLayout = ({ children }) => {
  return (
    <Stack horizontal tokens={{ childrenGap: 32 }}>
      <Nav />
      <div>{children}</div>
    </Stack>
  );
};

export default DashboardLayout;
