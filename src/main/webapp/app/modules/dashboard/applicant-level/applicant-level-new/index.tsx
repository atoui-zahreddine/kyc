import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import { ITag, PrimaryButton, Stack } from '@fluentui/react';

import GeneralDetails from 'app/modules/dashboard/applicant-level/applicant-level-new/components/general-details';
import Steps from 'app/modules/dashboard/applicant-level/applicant-level-new/components/steps';

import { IStep } from 'app/shared/model/step.model';
import { IdDocSetType } from 'app/shared/model/enumerations/id-doc-set-type.model';
import { TypeDoc } from 'app/shared/model/enumerations/type-doc.model';

import './styles.scss';
import { IDocSet } from 'app/shared/model/doc-set.model';
import { IApplicantLevel } from 'app/shared/model/applicant-level.model';
import { useAppDispatch } from 'app/config/store';
import { createEntity } from 'app/entities/applicant-level/applicant-level.reducer';

const stepExample = {
  name: '',
  docSets: [],
};

const NewApplicantLevel = () => {
  const { register, handleSubmit, getValues, setValue } = useForm();
  const [selectedSteps, setSelectedSteps] = useState<IStep[]>([{ ...stepExample }]);
  const dispatch = useAppDispatch();

  const submit = data => {
    const level: IApplicantLevel = {
      levelName: data.levelName,
      description: data.description,
      steps: selectedSteps,
    };
    dispatch(createEntity(level));
  };

  const addStep = () => {
    selectedSteps.push({ ...stepExample });
    setSelectedSteps([...selectedSteps]);
  };

  const changeStep = (option, index) => {
    selectedSteps[index].name = option.key;
    setSelectedSteps([...selectedSteps]);
  };

  const deleteStep = stepIndex => {
    const newSelectedSteps = selectedSteps.filter((item, index) => index !== stepIndex);
    setSelectedSteps(newSelectedSteps);
  };

  const updateStepDocSet = (docSets: ITag[], stepIndex: number) => {
    selectedSteps[stepIndex].docSets = docSets.map<IDocSet>(docSet => ({
      idDocSetType: IdDocSetType[selectedSteps[stepIndex].name],
      types: TypeDoc[docSet.key],
    }));
    setSelectedSteps([...selectedSteps]);
  };

  return (
    <Stack verticalFill tokens={{ childrenGap: '1rem' }}>
      <PrimaryButton styles={{ root: { alignSelf: 'flex-end' } }} onClick={handleSubmit(submit)}>
        Submit
      </PrimaryButton>
      <form className="new-level" onSubmit={handleSubmit(submit)}>
        <GeneralDetails getValues={getValues} register={register} setValue={setValue} />
        <Steps
          updateStepDocSet={updateStepDocSet}
          deleteStep={deleteStep}
          changeStep={changeStep}
          addStep={addStep}
          selectedSteps={selectedSteps}
        />
        <div className="new-level_link-container"></div>
      </form>
    </Stack>
  );
};

export default NewApplicantLevel;
