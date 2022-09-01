import React, { FunctionComponent } from 'react';
import { useWatch } from 'react-hook-form';
import { UseFormRegisterReturn } from 'react-hook-form/dist/types/form';
import { RegisterOptions } from 'react-hook-form/dist/types/validator';

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
  register: (name: string, options?: RegisterOptions) => UseFormRegisterReturn;
  setValue: any;
  control: any;
  errors: any;
}

const NewApplicantForm: FunctionComponent<NewApplicantFormProps> = ({ control, register, setValue, errors }) => {
  const level = useWatch({ control, name: 'level' }) as IApplicantLevel;

  const onChange = (value, target: string) => {
    console.warn(value, target);
    setValue(target, value);
  };

  const steps = {
    [`${IdDocSetType.IDENTITY}`]: (step: IStep) => <IdentiyDocuments step={step} control={control} onChangeHandler={onChange} />,
    [`${IdDocSetType.SELFIE}`]: (step: IStep) => (
      <SelfieDocuments step={step} setValue={setValue} control={control} onChangeHandler={onChange} />
    ),
    [`${IdDocSetType.PROOF_OF_RESIDENCE}`]: (step: IStep) => (
      <ProofOfResidenceDocuments step={step} setValue={setValue} control={control} onChangeHandler={onChange} />
    ),
    [`${IdDocSetType.PHONE_VERIFICATION}`]: (step: IStep) => (
      <PhoneRequiredInfo register={register} step={step} setValue={setValue} control={control} />
    ),
    [`${IdDocSetType.EMAIL_VERIFICATION}`]: (step: IStep) => (
      <EmailRequiredInfo register={register} step={step} setValue={setValue} control={control} />
    ),
  };

  return (
    <div className="new-applicant__form">
      <h3>Applicant Card</h3>
      <PersonalInfo register={register} onChange={onChange} setValue={setValue} />
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
