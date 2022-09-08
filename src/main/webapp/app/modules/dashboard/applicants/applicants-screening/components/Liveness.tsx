import { Text } from '@fluentui/react';
import React from 'react';
import DocumentCheckResultContainer from './common/document-check-result-container';

const Liveness = () => {
  return (
    <div>
      <Text variant="large">Liveness</Text>
      <DocumentCheckResultContainer name="Selfie" />
    </div>
  );
};

export default Liveness;
