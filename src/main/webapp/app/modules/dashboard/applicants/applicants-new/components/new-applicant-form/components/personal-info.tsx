import { ChoiceGroup, DatePicker, DayOfWeek, Dropdown, IChoiceGroupOption, Stack, TextField } from '@fluentui/react';
import React from 'react';

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
  { key: 'male', text: 'Male' },
  { key: 'female', text: 'Female' },
];

const PersonalInfo = ({ register, onChange, setValue }) => {
  return (
    <div>
      <h6 className="subtitle">Personal Info</h6>
      <Stack horizontal wrap horizontalAlign="space-between" styles={{ inner: { gap: '1rem' } }}>
        <TextField styles={inputStyle} label="First Name" {...register('firstName')} />
        <TextField styles={inputStyle} label="Last Name" {...register('lastName')} />
        <DatePicker
          firstDayOfWeek={DayOfWeek.Monday}
          label={'Birth Date'}
          styles={inputStyle}
          allowTextInput
          showMonthPickerAsOverlay={false}
          {...register('birthDate')}
          onSelectDate={date => setValue('birthDate', date)}
          placeholder="Select a date..."
          ariaLabel="Select a date"
        />
      </Stack>
      <Stack horizontal wrap horizontalAlign="space-between" styles={{ root: { marginTop: '1rem' }, inner: { gap: '3rem' } }}>
        <TextField styles={inputStyle} label="Email" {...register('email')} />
        <TextField styles={inputStyle} label="Phone" {...register('phone')} />
      </Stack>
      <Stack horizontal wrap horizontalAlign="space-between" styles={{ root: { marginTop: '1rem' }, inner: { gap: '3rem' } }}>
        <Dropdown
          label="Country"
          {...register('country')}
          onChange={(e, c) => onChange(c.key, 'country')}
          placeholder="Select a Country"
          options={dropdownControlledExampleOptions}
          styles={inputStyle}
        />
        <Dropdown
          label="Country of Birth"
          {...register('countryOfBirth')}
          onChange={(e, c) => onChange(c.key, 'countryOfBirth')}
          placeholder="Select a Country"
          options={dropdownControlledExampleOptions}
          styles={inputStyle}
        />
        <Dropdown
          label="Nationality"
          {...register('nationality')}
          onChange={(e, c) => onChange(c.key, 'nationality')}
          placeholder="Select a Nationality"
          options={dropdownControlledExampleOptions}
          styles={inputStyle}
        />
      </Stack>
      <Stack horizontal wrap horizontalAlign="space-between" styles={{ root: { marginTop: '1rem' }, inner: { gap: '3rem' } }}>
        <ChoiceGroup
          styles={{ flexContainer: { display: 'flex', gap: 16 } }}
          {...register('gender')}
          onChange={(e, c) => onChange(c.key, 'gender')}
          options={options}
          label="Gender"
        />
      </Stack>
    </div>
  );
};

export default PersonalInfo;
