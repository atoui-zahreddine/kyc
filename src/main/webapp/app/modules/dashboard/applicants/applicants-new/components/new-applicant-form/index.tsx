import React, { FunctionComponent } from 'react';
import { useWatch } from 'react-hook-form';

import { IdDocSetType } from 'app/shared/model/enumerations/id-doc-set-type.model';
import { IApplicantLevel } from 'app/shared/model/applicant-level.model';
import PersonalInfo from './components/personal-info';
import {
  EmailRequiredInfo,
  IdentiyDocuments,
  PhoneRequiredInfo,
  ProofOfResidenceDocuments,
  SelfieDocuments,
} from './components/required-documents';
import { IStep } from 'app/shared/model/step.model';

interface NewApplicantFormProps {
  setValue: any;
  control: any;
}

const NewApplicantForm: FunctionComponent<NewApplicantFormProps> = ({ control, setValue }) => {
  const level = useWatch({ control, name: 'level' }) as IApplicantLevel;

  const onChange = (value, target: string) => {
    setValue(target, value, { shouldValidate: true });
  };

  const steps = {
    [`${IdDocSetType.IDENTITY}`]: (step: IStep) => <IdentiyDocuments step={step} control={control} onChangeHandler={onChange} />,
    [`${IdDocSetType.SELFIE}`]: () => <SelfieDocuments control={control} onChangeHandler={onChange} />,
    [`${IdDocSetType.PROOF_OF_RESIDENCE}`]: () => <ProofOfResidenceDocuments control={control} onChangeHandler={onChange} />,
    [`${IdDocSetType.PHONE_VERIFICATION}`]: () => <PhoneRequiredInfo control={control} />,
    [`${IdDocSetType.EMAIL_VERIFICATION}`]: () => <EmailRequiredInfo control={control} />,
  };

  return (
    <div className="new-applicant__form">
      <h3>Applicant Card</h3>
      <PersonalInfo onChange={onChange} control={control} />
      {level &&
        level.steps.map(step => {
          return (
            <div className="requiredDocuments" key={step.name}>
              {steps[step.name](step)}
            </div>
          );
        })}
    </div>
  );
};
export default NewApplicantForm;
