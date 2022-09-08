import React, { FunctionComponent } from 'react';
import { Stack, Text } from '@fluentui/react';

interface ResultContainerProps {
  name: string;
  warnings: string;
  searchDate: string;
  status?: string;
}
const ResultContainer: FunctionComponent<ResultContainerProps> = ({ name, status, warnings, searchDate }) => {
  return (
    <Stack verticalAlign="center" horizontal wrap styles={{ inner: { gap: '6rem' } }}>
      <Stack verticalAlign="center" verticalFill styles={{ inner: { gap: '1rem' } }}>
        <Text variant="medium">{name}</Text>
        <Text
          variant="xSmall"
          styles={{
            root: {
              color: '#8B8B8B',
            },
          }}
        >
          {searchDate}
        </Text>
      </Stack>
      <Text variant="small" styles={{ root: { color: warnings !== 'CLEAN' ? '#EE2B2B' : '#757575' } }}>
        {warnings}
      </Text>
      <Text variant="small" styles={{ root: { marginLeft: 'auto' } }}>
        {status}
      </Text>
    </Stack>
  );
};

export default ResultContainer;
