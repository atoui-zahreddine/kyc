import { ChoiceGroup, DatePicker, DayOfWeek, Dropdown, IChoiceGroupOption, Stack, TextField } from '@fluentui/react';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import React from 'react';
import { Controller } from 'react-hook-form';

const inputStyle = { root: { flex: 1 } };
const dropdownControlledExampleOptions = [
  { key: 'apple', text: 'Apple' },
  { key: 'banana', text: 'Banana' },
  { key: 'orange', text: 'Orange' },
  { key: 'grape', text: 'Grape' },
  { key: 'broccoli', text: 'Broccoli' },
  { key: 'carrot', text: 'Carrot' },
  { key: 'lettuce', text: 'Lettuce' },
];

const options: IChoiceGroupOption[] = [
  { key: Gender.MALE, text: 'Male' },
  { key: Gender.FEMALE, text: 'Female' },
];

const PersonalInfo = ({ onChange, control }) => {
  return (
    <div>
      <h6 className="subtitle">Personal Info</h6>
      <Stack horizontal wrap horizontalAlign="space-between" styles={{ inner: { gap: '1rem' } }}>
        <Controller
          control={control}
          render={({ field: { ref, ...inputProps } }) => (
            <TextField styles={inputStyle} label="First Name" componentRef={ref} {...inputProps} />
          )}
          name="firstName"
        />
        <Controller
          control={control}
          render={({ field: { ref, ...inputProps } }) => (
            <TextField styles={inputStyle} label="Last Name" componentRef={ref} {...inputProps} />
          )}
          name="lastName"
        />
        <Controller
          control={control}
          render={({ field: { ref, name, ...inputProps } }) => (
            <DatePicker
              firstDayOfWeek={DayOfWeek.Monday}
              label={'Birth Date'}
              styles={inputStyle}
              onSelectDate={date => onChange(date, name)}
              placeholder="Select a date..."
              ariaLabel="Select a date"
              {...inputProps}
            />
          )}
          name="birthDate"
        />
      </Stack>
      <Stack horizontal wrap horizontalAlign="space-between" styles={{ root: { marginTop: '1rem' }, inner: { gap: '3rem' } }}>
        <Controller
          control={control}
          render={({ field: { ref, ...inputProps } }) => (
            <TextField styles={inputStyle} label="Email" {...inputProps} componentRef={ref} {...inputProps} />
          )}
          name="email"
        />
        <Controller
          control={control}
          render={({ field: { ref, ...inputProps } }) => (
            <TextField styles={inputStyle} label="Phone" {...inputProps} componentRef={ref} {...inputProps} />
          )}
          name="phone"
        />
      </Stack>
      <Stack horizontal wrap horizontalAlign="space-between" styles={{ root: { marginTop: '1rem' }, inner: { gap: '3rem' } }}>
        <Controller
          control={control}
          render={({ field: { ref, name, ...inputProps } }) => (
            <Dropdown
              label="Country"
              componentRef={ref}
              {...inputProps}
              onChange={(e, c) => onChange(c.key, name)}
              placeholder="Select a Country"
              options={dropdownControlledExampleOptions}
              styles={inputStyle}
            />
          )}
          name="country"
        />
        <Controller
          control={control}
          render={({ field: { ref, name, ...inputProps } }) => (
            <Dropdown
              label="Country of Birth"
              componentRef={ref}
              {...inputProps}
              onChange={(e, c) => onChange(c.key, name)}
              placeholder="Select a Country"
              options={dropdownControlledExampleOptions}
              styles={inputStyle}
            />
          )}
          name="countryOfBirth"
        />
        <Controller
          control={control}
          render={({ field: { ref, name, ...inputProps } }) => (
            <Dropdown
              label="Nationality"
              componentRef={ref}
              {...inputProps}
              onChange={(e, c) => onChange(c.key, name)}
              placeholder="Select a Nationality"
              options={dropdownControlledExampleOptions}
              styles={inputStyle}
            />
          )}
          name="nationality"
        />
      </Stack>
      <Stack horizontal wrap horizontalAlign="space-between" styles={{ root: { marginTop: '1rem' }, inner: { gap: '3rem' } }}>
        <Controller
          control={control}
          render={({ field: { ref, name, ...inputProps } }) => (
            <ChoiceGroup
              styles={{ flexContainer: { display: 'flex', gap: 16 } }}
              componentRef={ref}
              {...inputProps}
              onChange={(e, c) => onChange(c.key, name)}
              options={options}
              label="Gender"
            />
          )}
          name="gender"
        />
      </Stack>
    </div>
  );
};

export default PersonalInfo;
