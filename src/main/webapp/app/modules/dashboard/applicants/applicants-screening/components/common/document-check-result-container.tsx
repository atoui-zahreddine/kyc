import React, { FunctionComponent } from 'react';
import { Label, Pivot, PivotItem, Stack, Text } from '@fluentui/react';
import EmptyImage from 'app/assets/empty.png';

interface DocumentCheckResultContainerProps {
  isTwoSides?: boolean;
  name: string;
}

const DocumentCheckResultContainer: FunctionComponent<DocumentCheckResultContainerProps> = ({ isTwoSides, name }) => {
  return (
    <Stack horizontal horizontalAlign="space-between" styles={{ root: { gap: '7rem', marginTop: '1rem' } }}>
      <Stack verticalAlign="space-between" verticalFill styles={{ root: { flex: 1, gap: '1rem' } }}>
        <Text variant="mediumPlus">{name}</Text>
        {isTwoSides && (
          <Pivot styles={{ linkIsSelected: { ':before': { background: '#ccc' } } }}>
            <PivotItem headerText="Front"></PivotItem>
            <PivotItem headerText="Back"></PivotItem>
          </Pivot>
        )}
        <img src={EmptyImage} alt="empty image" />
      </Stack>
      <Stack styles={{ root: { flex: 1, alignSelf: 'center', gap: 16 } }}>
        <div>
          <Label>ID Authenticity Factor</Label>
          <Text variant="smallPlus">Measure of the how real and authentic the provided identification document appears to be</Text>
        </div>
        <Text variant="mediumPlus">Low (0%) WARNING</Text>
        <div>
          <Label>Valid document</Label>
          <Text variant="mediumPlus">NOT AVAILABLE</Text>
        </div>
      </Stack>
    </Stack>
  );
};

export default DocumentCheckResultContainer;
