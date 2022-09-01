import React, { FunctionComponent } from 'react';
import { TextField } from '@fluentui/react';
import { Controller } from 'react-hook-form';

interface GeneralDetailsProps {
  register: (name: string, RegisterOptions?) => { onChange; onBlur; name; ref };
  setValue: (name: string, value: unknown, config?: any) => void;
  getValues: (payload?: string | string[]) => any;
  control: any;
}

const GeneralDetails: FunctionComponent<GeneralDetailsProps> = ({ register, control, setValue, getValues }) => {
  return (
    <div className="new-level_general-details">
      <div className="new-level_general-details_title">
        <h3>General </h3>
      </div>

      <Controller
        control={control}
        render={({ field: { ref, ...inputProps } }) => <TextField label="Level Name" {...inputProps} componentRef={ref} />}
        name="levelName"
      />

      <Controller
        control={control}
        render={({ field: { ref, ...inputProps } }) => (
          <TextField label="Description" multiline autoAdjustHeight {...inputProps} componentRef={ref} />
        )}
        name="description"
      />

      {/* <div> */}
      {/*  <TerritoryChoiceGroup setValue={setValue} register={register} /> */}
      {/*  <CountriesDropdown register={register} getValues={getValues} setValue={setValue} /> */}
      {/* </div> */}

      {/* <TextField label="Privacy note text" multiline autoAdjustHeight {...register('privacyNote')} />*/}
    </div>
  );
};

export default GeneralDetails;
