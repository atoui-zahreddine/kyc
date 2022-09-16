import {
  DefaultButton,
  FontWeights,
  getTheme,
  IButtonStyles,
  IconButton,
  IIconProps,
  ILabelStyles,
  IStyleSet,
  Label,
  mergeStyleSets,
  Modal,
  Pivot,
  PivotItem,
  Spinner,
  SpinnerSize,
  Stack,
  Text,
} from '@fluentui/react';
import React, { FunctionComponent } from 'react';
import { DilisenseApiResponse } from 'app/shared/hooks/useDilisenseSanctionApi';
import { useBoolean, useId } from '@fluentui/react-hooks';

interface SanctionsProps {
  result: DilisenseApiResponse;
  loading: boolean;
  rerunCheck: () => void;
}

const labelStyles: Partial<IStyleSet<ILabelStyles>> = {
  root: { marginTop: 10 },
};

const theme = getTheme();
const contentStyles = mergeStyleSets({
  container: {
    display: 'flex',
    flexFlow: 'column nowrap',
    alignItems: 'stretch',
    width: '50%',
    minHeight: '50vh',
    maxHeight: '50vh',
  },
  header: [
    theme.fonts.large,
    {
      flex: '1 1 auto',
      borderTop: `4px solid ${theme.palette.themePrimary}`,
      color: theme.palette.neutralPrimary,
      display: 'flex',
      alignItems: 'center',
      fontWeight: FontWeights.semibold,
      padding: '12px 12px 14px 24px',
    },
  ],
  body: {
    flex: '4 4 auto',
    padding: '0 24px 24px 24px',
    overflowY: 'hidden',
    selectors: {
      p: { margin: '14px 0' },
      'p:first-child': { marginTop: 0 },
      'p:last-child': { marginBottom: 0 },
    },
  },
});

const iconButtonStyles: Partial<IButtonStyles> = {
  root: {
    color: theme.palette.neutralPrimary,
    marginLeft: 'auto',
    marginTop: '4px',
    marginRight: '2px',
  },
  rootHovered: {
    color: theme.palette.neutralDark,
  },
};

const cancelIcon: IIconProps = { iconName: 'Cancel' };

const Sanctions: FunctionComponent<SanctionsProps> = ({ result, loading, rerunCheck }) => {
  const [isModalOpen, { setTrue: showModal, setFalse: hideModal }] = useBoolean(false);
  const dilisenseResultModal = useId('dilisense-result');

  const isClean = result && result.found_records.length === 0;

  return (
    <div id={'Sanction.'} style={{ scrollMargin: '4rem' }}>
      <Stack verticalAlign="center" horizontal horizontalAlign="space-between" wrap styles={{ root: { marginBottom: '1rem' } }}>
        <Text styles={{ root: { display: 'inline-block' } }} variant="large">
          Sanctions
        </Text>
        <DefaultButton disabled={loading} onClick={rerunCheck} styles={{ root: { background: 'transparent', border: 'none' } }}>
          {loading ? <Spinner size={SpinnerSize.xSmall} /> : 'Re - Run Check'}
        </DefaultButton>
      </Stack>
      {!result ? (
        <></>
      ) : (
        <div>
          <Stack horizontal horizontalAlign="space-between">
            <Text variant="large">Dilisense</Text>
            <Text variant="large" styles={{ root: { color: isClean ? '#757575' : '#EE2B2B' } }}>
              {isClean ? 'Clean' : 'Suspicious'}
            </Text>
            <div>{!isClean && <DefaultButton onClick={showModal}>Details</DefaultButton>}</div>
          </Stack>
          <Modal
            titleAriaId={dilisenseResultModal}
            isOpen={isModalOpen}
            onDismiss={hideModal}
            isBlocking={false}
            containerClassName={contentStyles.container}
          >
            <div className={contentStyles.header}>
              <span id={dilisenseResultModal}>Dilisense API Result</span>
              <IconButton styles={iconButtonStyles} iconProps={cancelIcon} ariaLabel="Close popup modal" onClick={hideModal} />
            </div>
            <div className={contentStyles.body}>
              <Pivot aria-label="Screening result ">
                {result &&
                  result.found_records.map((record, index) => {
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
          </Modal>
        </div>
      )}
    </div>
  );
};

export default Sanctions;
