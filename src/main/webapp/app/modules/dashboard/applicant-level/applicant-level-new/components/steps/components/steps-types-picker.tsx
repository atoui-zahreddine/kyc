import { IBasePickerSuggestionsProps, ITag, TagPicker } from '@fluentui/react';
import React, { FunctionComponent, useMemo } from 'react';
import { TypeDoc } from 'app/shared/model/enumerations/type-doc.model';
import { IStep } from 'app/shared/model/step.model';
import { IDocSet } from 'app/shared/model/doc-set.model';

const pickerSuggestionsProps: IBasePickerSuggestionsProps = {
  suggestionsHeaderText: 'Suggested tags',
  noResultsFoundText: 'No color tags found',
};

const testTags: ITag[] = [TypeDoc.ID_CARD, TypeDoc.PASSPORT, TypeDoc.SELFIE, TypeDoc.DRIVERS, TypeDoc.RESIDENCE_PERMIT].map(item => ({
  key: item,
  name: item,
}));

const listContainsTagList = (tag: ITag, tagList?: ITag[]) => {
  if (!tagList || !tagList.length || tagList.length === 0) {
    return false;
  }
  return tagList.some(compareTag => compareTag.key === tag.key);
};

const filterSuggestedTags = (filterText: string, tagList: ITag[]): ITag[] => {
  return filterText
    ? testTags.filter(tag => tag.name.toLowerCase().indexOf(filterText.toLowerCase()) === 0 && !listContainsTagList(tag, tagList))
    : [];
};

const getTextFromItem = (item: ITag) => item.name;

const StepTypesPicker: FunctionComponent<{ updateStepDocSet; step: IStep; stepIndex: number }> = ({
  updateStepDocSet,
  step,
  stepIndex,
}) => {
  const selectedTags = useMemo(() => {
    return step.docSets.map(
      (docSet: IDocSet) =>
        ({
          key: docSet.types.toString(),
          name: docSet.types.toString(),
        } as ITag)
    );
  }, [step.docSets]);

  return (
    <TagPicker
      className="step__picker"
      removeButtonAriaLabel="Remove"
      selectionAriaLabel="Selected Sub Types"
      onResolveSuggestions={filterSuggestedTags}
      getTextFromItem={getTextFromItem}
      selectedItems={selectedTags}
      onChange={docSets => updateStepDocSet(docSets, stepIndex)}
      pickerSuggestionsProps={pickerSuggestionsProps}
    />
  );
};

export default StepTypesPicker;
