import React, { FunctionComponent, useEffect } from 'react';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity } from 'app/entities/applicant-info/applicant-info.reducer';
import { Label, Pivot, PivotItem, Stack, Text } from '@fluentui/react';
import './styles.scss';
import EmptyImage from 'app/assets/empty.png';

interface ResultContainerProps {
  name: string;
  warnings: string;
  searchDate: string;
  status?: string;
}
const ResultContainer: FunctionComponent<ResultContainerProps> = ({ name, status, warnings, searchDate }) => {
  return (
    <Stack verticalAlign="center" horizontal wrap styles={{ inner: { gap: '6rem' } }}>
      <Stack verticalAlign="center" verticalFill styles={{ inner: { gap: '1rem' } }}>
        <Text variant="medium">{name}</Text>
        <Text
          variant="xSmall"
          styles={{
            root: {
              color: '#8B8B8B',
            },
          }}
        >
          {searchDate}
        </Text>
      </Stack>
      <Text variant="small" styles={{ root: { color: warnings !== 'CLEAN' ? '#EE2B2B' : '#757575' } }}>
        {warnings}
      </Text>
      <Text variant="small" styles={{ root: { marginLeft: 'auto' } }}>
        {status}
      </Text>
    </Stack>
  );
};

const watchListData: ResultContainerProps[] = [
  {
    name: 'Adverse Media.',
    warnings: 'CLEAN',
    status: 'DONE',
    searchDate: 'Jul 19, 2022, 10:44:28 AM',
  },
  {
    name: 'Sanction.',
    warnings: 'CLEAN',
    status: 'DONE',
    searchDate: 'Jul 19, 2022, 10:44:28 AM',
  },
  {
    name: 'Warning.',
    warnings: '2 WARNINGS',
    status: 'DONE',
    searchDate: 'Jul 19, 2022, 10:44:28 AM',
  },
  {
    name: 'Fitness Probity.',
    warnings: 'CLEAN',
    status: 'DONE',
    searchDate: 'Jul 19, 2022, 10:44:28 AM',
  },
];

const checksData: ResultContainerProps[] = [
  {
    name: 'Drivers License',
    warnings: '2 WARNINGS',
    searchDate: 'Jul 19, 2022, 10:44:28 AM',
  },
  {
    name: 'Liveness',
    warnings: 'CLEAN',
    searchDate: 'Jul 19, 2022, 10:44:28 AM',
  },
  {
    name: 'WatchList',
    warnings: 'CLEAN',
    searchDate: 'Jul 19, 2022, 10:44:28 AM',
  },
];
interface DocumentCheckResultContainerProps {
  isTwoSides?: boolean;
  name: string;
}

const DocumentCheckResultContainer: FunctionComponent<DocumentCheckResultContainerProps> = ({ isTwoSides, name }) => {
  return (
    <Stack horizontal horizontalAlign="space-between" styles={{ root: { gap: '7rem', marginTop: '1rem' } }}>
      <Stack verticalAlign="space-between" verticalFill styles={{ root: { flex: 1, gap: '1rem' } }}>
        <Text variant="mediumPlus">{name}</Text>
        {isTwoSides && (
          <Pivot styles={{ linkIsSelected: { ':before': { background: '#ccc' } } }}>
            <PivotItem headerText="Front"></PivotItem>
            <PivotItem headerText="Back"></PivotItem>
          </Pivot>
        )}
        <img src={EmptyImage} alt="empty image" />
      </Stack>
      <Stack styles={{ root: { flex: 1, alignSelf: 'center', gap: 16 } }}>
        <div>
          <Label>ID Authenticity Factor</Label>
          <Text variant="smallPlus">Measure of the how real and authentic the provided identification document appears to be</Text>
        </div>
        <Text variant="mediumPlus">Low (0%) WARNING</Text>
        <div>
          <Label>Valid document</Label>
          <Text variant="mediumPlus">NOT AVAILABLE</Text>
        </div>
      </Stack>
    </Stack>
  );
};

const ApplicantsScreening = ({ match }) => {
  const { entity: applicantInfo } = useAppSelector(state => state.applicantInfo);
  const dispatch = useAppDispatch();

  useEffect(() => {
    const applicantId = match.params.id;
    if (applicantId) dispatch(getEntity(applicantId));
  }, [match.params.id]);

  return (
    <div className="level-result">
      <div>
        <Text variant="large">Details</Text>
        <Stack horizontal horizontalAlign="space-between" wrap styles={{ inner: { gap: 16 } }}>
          <Label>First Name: {applicantInfo && applicantInfo.firstName}</Label>
          <Label>Last Name: {applicantInfo && applicantInfo.lastName}</Label>
          <Label>Date of Birth: {applicantInfo && applicantInfo.dateOfBirth}</Label>
          <Label>gender: {applicantInfo && applicantInfo.gender}</Label>
          <Label>Nationality: {applicantInfo && applicantInfo.nationality}</Label>
          <Label>country of Birth: {applicantInfo && applicantInfo.countryOfBirth?.name}</Label>
        </Stack>
      </div>
      <div>
        <Text variant="large">Risk Level</Text>
        <Stack verticalAlign="center" horizontal horizontalAlign="space-between" wrap>
          <div>
            <Stack verticalAlign="center" horizontal horizontalAlign="space-between" wrap styles={{ inner: { gap: 16 } }}>
              <Text variant="medium">Checks Passed</Text>
              <Text variant="xSmall">1/3 passed</Text>
            </Stack>
            <Stack verticalAlign="center" horizontal horizontalAlign="space-between" wrap styles={{ inner: { gap: 16 } }}>
              <Text variant="medium">scoring</Text>
              <Text variant="xSmall">0% below average</Text>
            </Stack>
          </div>
          <div className="score-circle">
            <span>0%</span>
            <span>Confidence Score</span>
          </div>
        </Stack>
      </div>
      <div>
        <Stack verticalAlign="center" horizontal horizontalAlign="space-between" wrap>
          <Text variant="large">Checks</Text>
          <Text variant="medium">Trigger new Checks</Text>
        </Stack>

        {checksData.map(props => (
          <ResultContainer key={props.name} {...props} />
        ))}
      </div>
      <div>
        <Stack verticalAlign="center" horizontal horizontalAlign="space-between" wrap>
          <Stack verticalAlign="center" verticalFill>
            <Text variant="large">Watchlist</Text>
            <Text
              variant="mediumPlus"
              styles={{
                root: {
                  color: '#A0A0A0',
                },
              }}
            >
              3/5 Passed
            </Text>
          </Stack>
          <Text variant="medium">Re-Run Check</Text>
        </Stack>
        {watchListData.map(props => (
          <ResultContainer key={props.name} {...props} />
        ))}
      </div>
      <div>
        <Text variant="large">Identity Document</Text>
        <DocumentCheckResultContainer name={'Driver Licence'} isTwoSides />
      </div>
      <div>
        <Text variant="large">Liveness</Text>
        <DocumentCheckResultContainer name="Selfie" />
      </div>
    </div>
  );
};

export default ApplicantsScreening;
