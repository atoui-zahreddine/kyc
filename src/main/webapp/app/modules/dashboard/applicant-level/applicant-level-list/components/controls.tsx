import React from 'react';
import { useBoolean, useId } from '@fluentui/react-hooks';
import { Link as RouterLink, useLocation } from 'react-router-dom';
import { Callout, Icon, IconButton, Link, Stack } from '@fluentui/react';

const Controls = () => {
  const [isCalloutVisible, { toggle: toggleIsCalloutVisible }] = useBoolean(false);
  const location = useLocation();
  const buttonId = useId('callout-button');

  return (
    <>
      <IconButton id={buttonId} iconProps={{ iconName: 'more' }} aria-label="More" onClick={toggleIsCalloutVisible} />
      {isCalloutVisible && (
        <Callout role="dialog" target={`#${buttonId}`} onDismiss={toggleIsCalloutVisible} setInitialFocus>
          <Stack tokens={{ childrenGap: 16, padding: 8 }}>
            <Link as={RouterLink} to={`${location.pathname}/details`}>
              <Icon iconName="ComplianceAudit" aria-label="Details" /> Details
            </Link>
            <Link as={RouterLink} to={`${location.pathname}`}>
              <Icon iconName="Delete" aria-label="Delete" /> Delete
            </Link>
          </Stack>
        </Callout>
      )}
    </>
  );
};

export default Controls;
