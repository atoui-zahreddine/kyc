import { Text } from '@fluentui/react';
import React from 'react';
import DocumentCheckResultContainer from './common/document-check-result-container';

const IdentityDocuments = () => {
  return (
    <div>
      <Text variant="large">Identity Document</Text>
      <DocumentCheckResultContainer name={'Driver Licence'} isTwoSides />
    </div>
  );
};

export default IdentityDocuments;
