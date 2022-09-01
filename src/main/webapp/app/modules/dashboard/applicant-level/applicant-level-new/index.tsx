import React, { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { ITag, PrimaryButton, Spinner, SpinnerSize, Stack } from '@fluentui/react';

import GeneralDetails from 'app/modules/dashboard/applicant-level/applicant-level-new/components/general-details';
import Steps from 'app/modules/dashboard/applicant-level/applicant-level-new/components/steps';

import { IStep } from 'app/shared/model/step.model';
import { IdDocSetType } from 'app/shared/model/enumerations/id-doc-set-type.model';
import { TypeDoc } from 'app/shared/model/enumerations/type-doc.model';

import './styles.scss';
import { IDocSet } from 'app/shared/model/doc-set.model';
import { IApplicantLevel } from 'app/shared/model/applicant-level.model';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { createEntity, getEntity, updateEntity } from 'app/entities/applicant-level/applicant-level.reducer';
import { Redirect } from 'react-router-dom';
import _ from 'lodash';

const stepExample = {
  name: '',
  docSets: [],
};

const NewApplicantLevel = ({ match }) => {
  const { register, handleSubmit, getValues, setValue, control } = useForm();
  const [selectedSteps, setSelectedSteps] = useState<IStep[]>([{ ...stepExample }]);
  const { updating, updateSuccess, entities, entity } = useAppSelector(state => state.applicantLevel);
  const dispatch = useAppDispatch();

  const isUpdate = !!match.params.id;

  const getLevelFromStore = (id: number): IApplicantLevel => {
    const foundLevel = entities.filter(e => e.id === id);
    return foundLevel[0] || null;
  };

  const setLevelValues = (levelData: IApplicantLevel) => {
    setValue('id', levelData.id);
    setValue('levelName', levelData.levelName);
    setValue('description', levelData.description);
    setSelectedSteps(_.cloneDeep(levelData.steps));
  };

  const getLevel = levelId => {
    if (!levelId) return;
    const storeLevel = getLevelFromStore(levelId);
    if (!storeLevel) return dispatch(getEntity(levelId));

    setLevelValues(storeLevel);
  };

  useEffect(() => {
    const levelId = match.params.id;
    getLevel(levelId);
  }, [match.params.id]);

  useEffect(() => {
    if (entity?.id) {
      setLevelValues(entity);
    }
  }, [entity]);

  const submit = data => {
    const id = data?.id ? { id: data.id } : {};
    const levelData: IApplicantLevel = {
      ...id,
      levelName: data.levelName,
      description: data.description,
      steps: selectedSteps,
    };
    if (!isUpdate) dispatch(createEntity(levelData));
    else dispatch(updateEntity(levelData));
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

  if (updateSuccess) return <Redirect to={'/dashboard/applicant-levels'} />;

  return (
    <Stack verticalFill tokens={{ childrenGap: '1rem' }}>
      <PrimaryButton styles={{ root: { alignSelf: 'flex-end' } }} onClick={handleSubmit(submit)}>
        {!updating ? match.params.id ? 'Update' : 'Submit' : <Spinner size={SpinnerSize.medium} />}
      </PrimaryButton>
      <form className="new-level" onSubmit={handleSubmit(submit)}>
        <GeneralDetails control={control} getValues={getValues} register={register} setValue={setValue} />
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
