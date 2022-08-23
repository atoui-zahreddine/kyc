import React from 'react';
import { IconButton, IIconProps, Stack } from '@fluentui/react';
import { CommandButton } from '@fluentui/react/lib/Button';
import { IStep } from 'app/shared/model/step.model';
import './styles.scss';
import StepsDropdown from 'app/modules/dashboard/applicant-level/applicant-level-new/components/steps/components/steps-dropdown';
import StepTypesPicker from 'app/modules/dashboard/applicant-level/applicant-level-new/components/steps/components/steps-types-picker';

const addIcon: IIconProps = { iconName: 'Add' };

function DeleteStepButton(props: { onClick: () => any }) {
  return (
    <IconButton
      styles={{ root: { marginLeft: 'auto!important' } }}
      iconProps={{ iconName: 'Cancel', style: { color: 'red' } }}
      aria-label="Delete Step"
      onClick={props.onClick}
    />
  );
}

const Steps: React.FunctionComponent<{
  updateStepDocSet: any;
  deleteStep: any;
  changeStep: any;
  addStep?: any;
  selectedSteps: IStep[];
}> = ({ changeStep, updateStepDocSet, deleteStep, addStep, selectedSteps }) => {
  return (
    <div className="new-level_steps">
      <div className="new-level_steps_title">
        <h3>Steps </h3>
      </div>
      {selectedSteps.map((step, index) => (
        <div key={step.name} className="step">
          <Stack horizontal styles={{ root: { marginBottom: 16 } }}>
            <StepsDropdown step={step} onChange={(event, option) => changeStep(option, index)} />
            <StepTypesPicker updateStepDocSet={updateStepDocSet} stepIndex={index} step={step} />
            <DeleteStepButton onClick={() => deleteStep(index)} />
          </Stack>
        </div>
      ))}
      <CommandButton iconProps={addIcon} onClick={addStep} text="New Step" />
    </div>
  );
};

export default Steps;
