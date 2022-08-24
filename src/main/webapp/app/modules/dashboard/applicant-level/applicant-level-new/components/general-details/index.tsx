import React from 'react';
import { TextField } from '@fluentui/react';

const Index = ({ register, setValue, getValues }) => {
  return (
    <div className="new-level_general-details">
      <div className="new-level_general-details_title">
        <h3>General </h3>
      </div>

      <TextField label="Level Name" {...register('levelName')} />
      <TextField label="Description" multiline autoAdjustHeight {...register('description')} />

      {/* <div> */}
      {/*  <TerritoryChoiceGroup setValue={setValue} register={register} /> */}
      {/*  <CountriesDropdown register={register} getValues={getValues} setValue={setValue} /> */}
      {/* </div> */}

      {/* <TextField label="Privacy note text" multiline autoAdjustHeight {...register('privacyNote')} />*/}
    </div>
  );
};

export default Index;
