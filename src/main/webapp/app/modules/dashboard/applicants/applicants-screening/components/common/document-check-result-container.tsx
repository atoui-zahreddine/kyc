import React, { FunctionComponent, useEffect, useState } from 'react';
import { Label, Pivot, PivotItem, Stack, Text } from '@fluentui/react';
import { IApplicantDocs } from 'app/shared/model/applicant-docs.model';
import { Buffer } from 'buffer';
import axios from 'axios';

interface DocumentCheckResultContainerProps {
  isTwoSides?: boolean;
  name: string;
  doc: IApplicantDocs;
}

function useProtectedFiles(imageUrl: string) {
  const [fileBase64, setFileBase64] = useState<string>('');
  useEffect(() => {
    axios
      .get(`/api/files/${imageUrl}`, { responseType: 'arraybuffer' })
      .then(({ data, headers }) => {
        const base64 = Buffer.from(data, 'binary').toString('base64');
        const file = 'data:' + headers['content-type'] + ';base64,' + base64;
        setFileBase64(`data:image/png;base64,${base64}`);
      })
      .catch(console.error);
  }, [imageUrl]);
  return { fileBase64 };
}

const DocumentCheckResultContainer: FunctionComponent<DocumentCheckResultContainerProps> = ({ isTwoSides, name, doc }) => {
  const { fileBase64 } = useProtectedFiles(doc.imageUrl);
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
        <img src={fileBase64} alt="empty image" width="100%" height="100%" />
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
