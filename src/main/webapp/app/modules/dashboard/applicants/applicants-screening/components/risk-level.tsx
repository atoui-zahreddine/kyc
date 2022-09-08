import { Stack, Text } from '@fluentui/react';
import React from 'react';

const RiskLevel = () => {
  return (
    <div>
      <Text variant="large">Risk Level</Text>
      <Stack verticalAlign="center" horizontal horizontalAlign="space-between" wrap>
        <div>
          <Stack verticalAlign="center" horizontal horizontalAlign="space-between" wrap styles={{ inner: { gap: 16 } }}>
            <Text variant="medium">Checks Passed</Text>
            <Text variant="xSmall">1/3 passed</Text>
          </Stack>
          <Stack verticalAlign="center" horizontal horizontalAlign="space-between" wrap styles={{ inner: { gap: 16 } }}>
            <Text variant="medium">scoring</Text>
            <Text variant="xSmall">0% below average</Text>
          </Stack>
        </div>
        <div className="score-circle">
          <span>0%</span>
          <span>Confidence Score</span>
        </div>
      </Stack>
    </div>
  );
};

export default RiskLevel;
