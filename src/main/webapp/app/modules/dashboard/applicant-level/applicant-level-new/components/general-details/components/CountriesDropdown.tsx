import React from 'react';
import { Dropdown, DropdownMenuItemType, IDropdownOption, IDropdownStyles, warn } from '@fluentui/react';

const countries: IDropdownOption[] = [
  { key: 'fruitsHeader', text: 'Fruits', itemType: DropdownMenuItemType.Header },
  { key: 'apple', text: 'Apple' },
  { key: 'banana', text: 'Banana' },
  { key: 'orange', text: 'Orange' },
  { key: 'grape', text: 'Grape' },
  { key: 'divider_1', text: '-', itemType: DropdownMenuItemType.Divider },
  { key: 'vegetablesHeader', text: 'Vegetables', itemType: DropdownMenuItemType.Header },
  { key: 'broccoli', text: 'Broccoli' },
  { key: 'carrot', text: 'Carrot' },
];

const dropdownStyles: Partial<IDropdownStyles> = {
  dropdown: { marginTop: '1rem' },
};

function CountriesDropdown({ setValue, getValues, register }) {
  const onChange = (event: React.FormEvent<HTMLDivElement>, option?: IDropdownOption) => {
    const selectedCountries = getValues('countries') || [];
    if (!option.selected) selectedCountries.remove(option.key);
    else selectedCountries.push(option.key);
    setValue('countries', selectedCountries);
  };

  return (
    <Dropdown
      placeholder={`Select included countries`}
      multiSelect
      options={countries}
      styles={dropdownStyles}
      {...register('countries')}
      onChange={onChange}
    />
  );
}

export default CountriesDropdown;
