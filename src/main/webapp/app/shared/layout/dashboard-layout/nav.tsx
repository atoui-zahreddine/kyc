import * as React from 'react';
import { Nav, INavLink, INavStyles, INavLinkGroup } from '@fluentui/react/lib/Nav';
import { NavLink as RouterLink, useHistory } from 'react-router-dom';
import { Link } from '@fluentui/react';

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
        url: '/',
        expandAriaLabel: 'Expand Home section',
        key: 'dashboard',
      },
      {
        name: 'Applicants',
        url: '/applicants',
        key: 'applicants',
      },
      {
        name: 'Statistics',
        url: '/statistics',
        key: 'statistics',
      },
      {
        name: 'Integrations',
        url: '',
        key: 'integrations',
        isExpanded: true,
        links: [
          {
            name: 'Application Levels',
            url: '/applicant-levels',
            key: 'application-level',
          },
          {
            name: 'Questionnaires',
            url: '/questionnaires',
            key: 'questionnaires',
          },
          {
            name: 'Scoring Engine',
            url: '/scoring-engine',
            key: 'scoring-engine',
          },
        ],
      },
      {
        name: 'Dev Space',
        url: '',
        key: 'dev-space',
        isExpanded: true,
        links: [
          {
            name: 'Webhooks',
            url: '/webhooks',
            key: 'webhooks',
          },
          {
            name: 'App Tokens',
            url: '/app-tokens',
            key: 'app-tokens',
          },
          {
            name: 'Docs',
            url: '/docs',
            key: 'docs',
          },
          {
            name: 'Api Logs',
            url: '/api-logs',
            key: 'api-logs',
          },
        ],
      },
    ],
  },
];

const DASHBOARD_PATH = '/dashboard';

const NavBasicExample: React.FunctionComponent = props => {
  const history = useHistory();

  const onNavClick = (ev?: React.MouseEvent<HTMLElement>, item?: INavLink) => {
    ev.preventDefault();
    item && item?.url && history.push(DASHBOARD_PATH + item.url);
  };
  return <Nav onLinkClick={onNavClick} styles={navStyles} groups={navLinkGroups} />;
};

export default NavBasicExample;
