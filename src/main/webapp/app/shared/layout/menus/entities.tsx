import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';

import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu" data-cy="entity" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/applicant">
      Applicant
    </MenuItem>
    <MenuItem icon="asterisk" to="/ip-info">
      Ip Info
    </MenuItem>
    <MenuItem icon="asterisk" to="/user-agent-info">
      User Agent Info
    </MenuItem>
    <MenuItem icon="asterisk" to="/applicant-info">
      Applicant Info
    </MenuItem>
    <MenuItem icon="asterisk" to="/country">
      Country
    </MenuItem>
    <MenuItem icon="asterisk" to="/applicant-docs">
      Applicant Docs
    </MenuItem>
    <MenuItem icon="asterisk" to="/applicant-phone">
      Applicant Phone
    </MenuItem>
    <MenuItem icon="asterisk" to="/applicant-addresse">
      Applicant Addresse
    </MenuItem>
    <MenuItem icon="asterisk" to="/applicant-level">
      Applicant Level
    </MenuItem>
    <MenuItem icon="asterisk" to="/step">
      Step
    </MenuItem>
    <MenuItem icon="asterisk" to="/doc-set">
      Doc Set
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
