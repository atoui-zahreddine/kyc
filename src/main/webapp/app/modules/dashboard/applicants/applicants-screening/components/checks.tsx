import { Stack, Text } from '@fluentui/react';
import React, { FunctionComponent } from 'react';
import ResultContainer from './common/result-container';

interface ChecksProps {
  checksData: {
    name: string;
    warnings: string;
    searchDate: string;
  }[];
}

const Checks: FunctionComponent<ChecksProps> = ({ checksData }) => {
  return (
    <div>
      <Stack verticalAlign="center" horizontal horizontalAlign="space-between" wrap>
        <Text variant="large">Checks</Text>
        <Text variant="medium">Trigger new Checks</Text>
      </Stack>

      {checksData.map(props => (
        <ResultContainer key={props.name} {...props} />
      ))}
    </div>
  );
};

export default Checks;
