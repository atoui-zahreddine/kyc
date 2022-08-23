import { IStep } from 'app/shared/model/step.model';
import { Dropdown } from '@fluentui/react';
import { IdDocSetType } from 'app/shared/model/enumerations/id-doc-set-type.model';
import React from 'react';

const StepsDropdown = (props: { step: IStep; onChange: (event, option) => void }) => {
  return (
    <Dropdown
      placeholder="Select a Step"
      styles={{ root: { width: 150 } }}
      options={[
        {
          key: IdDocSetType.IDENTITY,
          text: 'Identity',
        },
        {
          key: IdDocSetType.SELFIE,
          text: 'Selfie',
        },
        {
          key: IdDocSetType.PROOF_OF_RESIDENCE,
          text: 'Proof of residence',
        },
        {
          key: IdDocSetType.PHONE_VERIFICATION,
          text: ' Phone verification',
        },
        {
          key: IdDocSetType.EMAIL_VERIFICATION,
          text: ' Email verification',
        },
      ]}
      selectedKey={props.step.name}
      onChange={props.onChange}
    />
  );
};

export default StepsDropdown;
