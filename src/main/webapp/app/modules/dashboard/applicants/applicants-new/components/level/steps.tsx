import React, { FunctionComponent } from 'react';
import { IStep } from 'app/shared/model/step.model';
import { FontIcon, Stack } from '@fluentui/react';

interface StepsProps {
  steps: IStep[];
}

const Steps: FunctionComponent<StepsProps> = ({ steps }) => {
  return (
    <div className="new-applicant__level__steps">
      {steps.map(step => (
        <Stack horizontal key={step.name} verticalAlign="start" tokens={{ childrenGap: 16 }} styles={{ root: { marginBottom: '1rem' } }}>
          <FontIcon className="new-applicant__level__steps__icon" iconName="FullCircleMask" />
          <Stack verticalFill tokens={{ childrenGap: 2 }}>
            <h6>{step.name}</h6>
            <Stack horizontal tokens={{ childrenGap: 5 }}>
              {step.docSets.map(docSets => (
                <span key={docSets.types}>{docSets.types} .</span>
              ))}
            </Stack>
          </Stack>
        </Stack>
      ))}
    </div>
  );
};

export default Steps;
