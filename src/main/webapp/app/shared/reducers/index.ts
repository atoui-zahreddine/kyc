import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication from './authentication';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import userManagement from 'app/modules/administration/user-management/user-management.reducer';
import register from 'app/modules/account/register/register.reducer';
import activate from 'app/modules/account/activate/activate.reducer';
import password from 'app/modules/account/password/password.reducer';
import settings from 'app/modules/account/settings/settings.reducer';
import passwordReset from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import applicant from 'app/entities/applicant/applicant.reducer';
// prettier-ignore
import ipInfo from 'app/entities/ip-info/ip-info.reducer';
// prettier-ignore
import userAgentInfo from 'app/entities/user-agent-info/user-agent-info.reducer';
// prettier-ignore
import applicantInfo from 'app/entities/applicant-info/applicant-info.reducer';
// prettier-ignore
import country from 'app/entities/country/country.reducer';
// prettier-ignore
import applicantDocs from 'app/entities/applicant-docs/applicant-docs.reducer';
// prettier-ignore
import applicantPhone from 'app/entities/applicant-phone/applicant-phone.reducer';
// prettier-ignore
import applicantAddresse from 'app/entities/applicant-addresse/applicant-addresse.reducer';
// prettier-ignore
import applicantLevel from 'app/entities/applicant-level/applicant-level.reducer';
// prettier-ignore
import step from 'app/entities/step/step.reducer';
// prettier-ignore
import docSet from 'app/entities/doc-set/doc-set.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  applicant,
  ipInfo,
  userAgentInfo,
  applicantInfo,
  country,
  applicantDocs,
  applicantPhone,
  applicantAddresse,
  applicantLevel,
  step,
  docSet,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
