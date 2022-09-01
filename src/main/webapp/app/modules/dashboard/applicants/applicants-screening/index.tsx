import React, { useEffect, useState } from 'react';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity } from 'app/entities/applicant-info/applicant-info.reducer';
import axiosPublicInstance from 'app/config/axiosPublicInstance';
import { ILabelStyles, IStyleSet, Label, Pivot, PivotItem, Text } from '@fluentui/react';

const dilisenseApi = 'https://site-api.dilisense.com/website/search';

const labelStyles: Partial<IStyleSet<ILabelStyles>> = {
  root: { marginTop: 10 },
};

interface IScreeningData {
  foundRecords?: Array<any>;
  totalHits?: number;
  fuzzySearchApplied?: boolean;
}

const ApplicantsScreening = ({ match }) => {
  const { entity: applicantInfo } = useAppSelector(state => state.applicantInfo);
  const dispatch = useAppDispatch();
  const [screeningData, setScreeningData] = useState<IScreeningData>();

  const getScreeningResult = async () => {
    if (applicantInfo?.firstName && applicantInfo?.lastName) {
      const { data } = await axiosPublicInstance.post(dilisenseApi, {
        query: applicantInfo.firstName + ' ' + applicantInfo?.lastName,
        offset: 0,
        sourceType: null,
        firstTimeVisitor: true,
      });

      setScreeningData(data);
    }
  };

  useEffect(() => {
    const applicantId = match.params.id;
    if (applicantId) dispatch(getEntity(applicantId));
  }, [match.params.id]);

  useEffect(() => {
    getScreeningResult();
  }, [applicantInfo]);

  return (
    <div>
      <Text variant="large">Applicant {`${applicantInfo.firstName || ''} ${applicantInfo.lastName || ''} `}screening</Text>
      <Pivot aria-label="Screening result ">
        {screeningData &&
          screeningData.foundRecords.map((record, index) => {
            return (
              <PivotItem
                key={index}
                headerText={'' + (index + 1)}
                headerButtonProps={{
                  'data-order': index + 1,
                }}
              >
                {Object.keys(record).map(key => (
                  <Label key={key} styles={labelStyles}>
                    {key} : {typeof record[key] === 'string' ? record[key] : record[key].join(' ')}
                  </Label>
                ))}
              </PivotItem>
            );
          })}
      </Pivot>
    </div>
  );
};

export default ApplicantsScreening;
