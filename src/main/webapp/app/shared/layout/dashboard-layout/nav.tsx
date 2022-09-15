import * as React from 'react';
import { INavLink, INavLinkGroup, INavStyles, Nav } from '@fluentui/react/lib/Nav';
import { useHistory } from 'react-router-dom';

const DASHBOARD_PATH = '/dashboard';

const navStyles: Partial<INavStyles> = {
  root: {
    width: 250,
    height: '100vh-80px',
    boxSizing: 'border-box',
    border: '1px solid #eee',
    overflowY: 'auto',
  },
};

const navLinkGroups: INavLinkGroup[] = [
  {
    links: [
      {
        name: 'Dashboard',

        key: DASHBOARD_PATH + '/',
        url: DASHBOARD_PATH + '/',
      },
      {
        name: 'Applicants',
        key: DASHBOARD_PATH + '/applicants',
        url: DASHBOARD_PATH + '/applicants',
      },
      {
        name: 'Statistics',
        key: DASHBOARD_PATH + '/statistics',
        url: DASHBOARD_PATH + '/statistics',
      },
      {
        name: 'Integrations',
        url: '',
        key: DASHBOARD_PATH + '/integrations',
        isExpanded: true,
        links: [
          {
            name: 'Applicant Levels',
            key: DASHBOARD_PATH + '/applicant-levels',
            url: DASHBOARD_PATH + '/applicant-levels',
          },
          {
            name: 'Questionnaires',
            key: DASHBOARD_PATH + '/questionnaires',
            url: DASHBOARD_PATH + '/questionnaires',
          },
          {
            name: 'Scoring Engine',
            key: DASHBOARD_PATH + '/scoring-engine',
            url: DASHBOARD_PATH + '/scoring-engine',
          },
        ],
      },
      {
        name: 'Dev Space',
        url: '',
        key: DASHBOARD_PATH + '/dev-space',
        isExpanded: true,
        links: [
          {
            name: 'Webhooks',
            key: DASHBOARD_PATH + '/webhooks',
            url: DASHBOARD_PATH + '/webhooks',
          },
          {
            name: 'App Tokens',
            key: DASHBOARD_PATH + '/app-tokens',
            url: DASHBOARD_PATH + '/app-tokens',
          },
          {
            name: 'Docs',
            key: DASHBOARD_PATH + '/docs',
            url: DASHBOARD_PATH + '/docs',
          },
          {
            name: 'Api Logs',
            key: DASHBOARD_PATH + '/api-logs',
            url: DASHBOARD_PATH + '/api-logs',
          },
        ],
      },
    ],
  },
];

const NavBasicExample: React.FunctionComponent = () => {
  const history = useHistory();

  const onNavClick = (ev?: React.MouseEvent<HTMLElement>, item?: INavLink) => {
    ev.preventDefault();
    item && item?.url && history.push(item?.url);
  };
  return <Nav onLinkClick={onNavClick} selectedKey={history.location.pathname} styles={navStyles} groups={navLinkGroups} />;
};

export default NavBasicExample;
