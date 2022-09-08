import React, { FunctionComponent, useEffect, useMemo, useState } from 'react';
import { RegisterOptions } from 'react-hook-form/dist/types/validator';
import { UseFormRegisterReturn } from 'react-hook-form/dist/types/form';
import { Dropdown, IDropdownOption, IDropdownStyles } from '@fluentui/react';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities } from 'app/entities/applicant-level/applicant-level.reducer';
import { IApplicantLevel } from 'app/shared/model/applicant-level.model';
import Steps from 'app/modules/dashboard/applicants/applicants-new/components/level/steps';
import { Link } from 'react-router-dom';

interface LevelProps {
  register: (name: string, options?: RegisterOptions) => UseFormRegisterReturn;
  setValue: any;
}

const dropdownStyles: Partial<IDropdownStyles> = {
  dropdown: { marginTop: '1rem' },
};

const Level: FunctionComponent<LevelProps> = ({ register, setValue }) => {
  const levels = useAppSelector(state => state.applicantLevel.entities);
  const [selectedLevel, setSelectedLevel] = useState<IApplicantLevel>();
  const dispatch = useAppDispatch();

  const levelLink = selectedLevel?.id ? '/details/' + selectedLevel?.id : '';

  const dropDownLevels = useMemo<IDropdownOption[]>(() => {
    return levels?.length
      ? levels.map(
          level =>
            ({
              key: level.id,
              text: level.levelName,
            } as IDropdownOption)
        )
      : [];
  }, [levels]);

  useEffect(() => {
    dispatch(getEntities({ sort: 'id,desc' }));
  }, []);
  const onChange = (event: React.FormEvent<HTMLDivElement>, option?: IDropdownOption, index?: number) => {
    const level = levels.filter(l => l.id === option.key)[0];
    setValue('level', level);
    setSelectedLevel(level);
  };
  return (
    <div className="new-applicant__level">
      <h3>Required Steps</h3>
      <Dropdown
        placeholder={`Select a Level`}
        options={dropDownLevels}
        styles={dropdownStyles}
        {...register('level')}
        onChange={onChange}
      />
      <div className="new-applicant__level__settings">
        <span>Customize the level to add extra steps</span>
        <Link to={`/dashboard/applicant-levels${levelLink}`} className="link">
          go to level settings
        </Link>
      </div>
      {selectedLevel?.steps && <Steps steps={selectedLevel.steps} />}
    </div>
  );
};
export default Level;
