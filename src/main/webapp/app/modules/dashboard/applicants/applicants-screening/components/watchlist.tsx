import { Stack, Text } from '@fluentui/react';
import React, { FunctionComponent } from 'react';
import ResultContainer from 'app/modules/dashboard/applicants/applicants-screening/components/common/result-container';

interface WatchListProps {
  watchListData: {
    name: string;
    warnings: string;
    searchDate: string;
    status?: string;
  }[];
}

const WatchList: FunctionComponent<WatchListProps> = ({ watchListData }) => {
  return (
    <div>
      <Stack verticalAlign="center" horizontal horizontalAlign="space-between" wrap>
        <Stack verticalAlign="center" verticalFill>
          <Text variant="large">Watchlist</Text>
          <Text
            variant="mediumPlus"
            styles={{
              root: {
                color: '#A0A0A0',
              },
            }}
          >
            3/5 Passed
          </Text>
        </Stack>
        <Text variant="medium">Re-Run Check</Text>
      </Stack>
      {watchListData.map(props => (
        <ResultContainer key={props.name} {...props} />
      ))}
    </div>
  );
};

export default WatchList;
