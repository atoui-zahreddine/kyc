import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Applicant from './applicant';
import IpInfo from './ip-info';
import UserAgentInfo from './user-agent-info';
import ApplicantInfo from './applicant-info';
import Country from './country';
import ApplicantDocs from './applicant-docs';
import ApplicantPhone from './applicant-phone';
import ApplicantAddresse from './applicant-addresse';
import ApplicantLevel from './applicant-level';
import Step from './step';
import DocSet from './doc-set';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}applicant`} component={Applicant} />
      <ErrorBoundaryRoute path={`${match.url}ip-info`} component={IpInfo} />
      <ErrorBoundaryRoute path={`${match.url}user-agent-info`} component={UserAgentInfo} />
      <ErrorBoundaryRoute path={`${match.url}applicant-info`} component={ApplicantInfo} />
      <ErrorBoundaryRoute path={`${match.url}country`} component={Country} />
      <ErrorBoundaryRoute path={`${match.url}applicant-docs`} component={ApplicantDocs} />
      <ErrorBoundaryRoute path={`${match.url}applicant-phone`} component={ApplicantPhone} />
      <ErrorBoundaryRoute path={`${match.url}applicant-addresse`} component={ApplicantAddresse} />
      <ErrorBoundaryRoute path={`${match.url}applicant-level`} component={ApplicantLevel} />
      <ErrorBoundaryRoute path={`${match.url}step`} component={Step} />
      <ErrorBoundaryRoute path={`${match.url}doc-set`} component={DocSet} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
