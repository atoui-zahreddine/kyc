import React from 'react';
import { ChoiceGroup, IChoiceGroupOption } from '@fluentui/react';

const territoryOptions: IChoiceGroupOption[] = [
  { key: 'include', value: 'include', text: 'Include' },
  { key: 'exclude', value: 'exclude', text: 'Exclude' },
];

function TerritoryChoiceGroup({ register, setValue }) {
  const onChange = (event: React.FormEvent<HTMLDivElement>, option?: IChoiceGroupOption) => {
    setValue('territory', option.key);
  };
  return <ChoiceGroup options={territoryOptions} label="Countries and territory" {...register('territory')} onChange={onChange} />;
}

export default TerritoryChoiceGroup;
