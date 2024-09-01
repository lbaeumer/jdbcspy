package de.luisoft.jdbcspy.vendor;

import de.luisoft.jdbcspy.ClientProperties;
import de.luisoft.jdbcspy.ProxyXADatasource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DB2ProxyXADatasource extends ProxyXADatasource {

    public DB2ProxyXADatasource() {
        super(ClientProperties.DB_DB2_XA_DATASOURCE_CLASS);
    }

    public void setRetrieveMessagesFromServerOnGetMessage(boolean flag) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setRetrieveMessagesFromServerOnGetMessage", boolean.class);
        m.invoke(uDatasource, flag);
    }

    public void setAccessToken(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setAccessToken", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setAccountingInterval(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setAccountingInterval", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setActivateDatabase(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setActivateDatabase", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setAffinityFailbackInterval(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setAffinityFailbackInterval", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setAllowNextOnExhaustedResultSet(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setAllowNextOnExhaustedResultSet", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setAllowNullResultSetForExecuteQuery(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setAllowNullResultSetForExecuteQuery", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setAllowUnassignedParameters(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setAllowUnassignedParameters", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setAlternateGroupDatabaseName(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setAlternateGroupDatabaseName", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setAlternateGroupPortNumber(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setAlternateGroupPortNumber", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setAlternateGroupServerName(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setAlternateGroupServerName", String.class);
        m.invoke(uDatasource, s);
    }

    public void setApiKey(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setApiKey", String.class);
        m.invoke(uDatasource, s);
    }

    public void setAtomicMultiRowInsert(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setAtomicMultiRowInsert", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setAutoCommit(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setAutoCommit", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setBidiLayoutTransformationFlag(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setBidiLayoutTransformationFlag", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setBlockingReadConnectionTimeout(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setBlockingReadConnectionTimeout", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setCharOutputSize(short i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setCharOutputSize", short.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setClientAccountingInformation(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setClientAccountingInformation", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setClientApplcompat(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setClientApplcompat", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setClientApplicationInformation(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setClientApplicationInformation", boolean.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setClientBidiStringType(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setClientBidiStringType", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setClientCorrelationToken(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setClientCorrelationToken", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setClientDebugInfo(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setClientDebugInfo", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setClientProgramId(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setClientProgramId", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setClientProgramName(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setClientProgramName", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setClientRerouteAlternatePortNumber(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setClientRerouteAlternatePortNumber", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setClientRerouteAlternateServerName(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setClientRerouteAlternateServerName", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setClientRerouteServerListJNDIName(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setClientRerouteServerListJNDIName", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setClientUser(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setClientUser", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setClientWorkstation(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setClientWorkstation", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setCliSchema(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setCliSchema", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setCommandTimeout(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setCommandTimeout", int.class);
        m.invoke(uDatasource, i);
    }

    public void setConcurrentAccessResolution(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setConcurrentAccessResolution", int.class);
        m.invoke(uDatasource, i);
    }

    public void setConnectionCloseWithInFlightTransaction(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setConnectionCloseWithInFlightTransaction", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setConnectionTimeout(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setConnectionTimeout", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setConnectNode(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setConnectNode", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setCR_LOCKBLOB(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setCR_LOCKBLOB", String.class);
        m.invoke(uDatasource, s);
    }

    public void setCreateLicenseCache(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setCreateLicenseCache", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setCurrentAlternateGroupEntry(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setCurrentAlternateGroupEntry", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setCurrentDegree(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setCurrentDegree", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setCurrentExplainMode(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setCurrentExplainMode", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setCurrentExplainSnapshot(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setCurrentExplainSnapshot", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setCurrentFunctionPath(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setCurrentFunctionPath", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setCurrentLocaleLcCtype(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setCurrentLocaleLcCtype", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setCurrentLockTimeout(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setCurrentLockTimeout", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setCurrentMaintainedTableTypesForOptimization(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setCurrentMaintainedTableTypesForOptimization", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setCurrentPackagePath(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setCurrentPackagePath", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setCurrentPackageSet(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setCurrentPackageSet", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setCurrentQueryOptimization(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setCurrentQueryOptimization", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setCurrentRefreshAge(long l) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setCurrentRefreshAge", long.class);
        m.invoke(uDatasource, l);
    }

    public synchronized void setCurrentSchema(String currentSchema) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setCurrentSchema", String.class);
        m.invoke(uDatasource, currentSchema);
    }

    public synchronized void setCurrentSQLID(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setCurrentSQLID", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setCursorSensitivity(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setCursorSensitivity", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setDataSourceName(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDataSourceName", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setDateFormat(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDateFormat", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setDateTimeMutation(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDateTimeMutation", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setDB_LOCALE(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDB_LOCALE", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setDBANSIWARN(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDBANSIWARN", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setDBDATE(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDBDATE", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setDBMAXPROC(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDBMAXPROC", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setDBPATH(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDBPATH", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setDBSPACETEMP(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDBSPACETEMP", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setDBTEMP(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDBTEMP", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setDBUPSPACE(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDBUPSPACE", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setDEBUG(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDEBUG", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setDecimalRoundingMode(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDecimalRoundingMode", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setDecimalSeparator(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDecimalSeparator", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setDecimalStringFormat(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDecimalStringFormat", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setDefaultIsolationLevel(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDefaultIsolationLevel", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setDeferPrepares(boolean flag) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDeferPrepares", boolean.class);
        m.invoke(uDatasource, flag);
    }

    public synchronized void setDELIMIDENT(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDELIMIDENT", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setDescription(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDescription", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setDisableTimezone(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDisableTimezone", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setDowngradeHoldCursorsUnderXa(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDowngradeHoldCursorsUnderXa", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setDUMPCORE(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDUMPCORE", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setDUMPDIR(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDUMPDIR", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setDUMPMEM(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDUMPMEM", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setDUMPSHMEM(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setDUMPSHMEM", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setEmulateParameterMetaDataForZCalls(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setEmulateParameterMetaDataForZCalls", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setEnableAlternateGroupSeamlessACR(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setEnableAlternateGroupSeamlessACR", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setEnableBidiLayoutTransformation(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setEnableBidiLayoutTransformation", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setEnableClientAffinitiesList(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setEnableClientAffinitiesList", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setEnableConnectionConcentrator(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setEnableConnectionConcentrator", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setEnableExtendedDescribe(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setEnableExtendedDescribe", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setEnableExtendedIndicators(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setEnableExtendedIndicators", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setEnableMultirowInsertSupport(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setEnableMultirowInsertSupport", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setEnableNamedParameterMarkers(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setEnableNamedParameterMarkers", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setEnableRowsetSupport(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setEnableRowsetSupport", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setEnableSeamlessFailover(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setEnableSeamlessFailover", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setEnableSysplexWLB(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setEnableSysplexWLB", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setEnableT2zosCallSPBundling(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setEnableT2zosCallSPBundling", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setEnableT2zosLBF(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setEnableT2zosLBF", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setEnableT2zosLBFSPResultSets(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setEnableT2zosLBFSPResultSets", int.class);
        m.invoke(uDatasource, i);
    }

    public void setEnableTimeoutOnCursor(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setEnableTimeoutOnCursor", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setEnableXACleanTransaction(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setEnableXACleanTransaction", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setEncryptionAlgorithm(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setEncryptionAlgorithm", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setExtendedDiagnosticLevel(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setExtendedDiagnosticLevel", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setExtendedTableInfo(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setExtendedTableInfo", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setFetchSize(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setFetchSize", int.class);
        m.invoke(uDatasource, i);
    }

    public void setFirstConnectionFlown(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setFirstConnectionFlown", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setFloatingPointStringFormat(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setFloatingPointStringFormat", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setFullyMaterializeInputStreams(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setFullyMaterializeInputStreams", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setFullyMaterializeInputStreamsOnBatchExecution(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setFullyMaterializeInputStreamsOnBatchExecution", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setFullyMaterializeLobData(boolean fullyMaterializeLobData) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setFullyMaterializeLobData", boolean.class);
        m.invoke(uDatasource, fullyMaterializeLobData);
    }

    public synchronized void setGCORE(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setGCORE", boolean.class);
        m.invoke(uDatasource, s);
    }

    public void setGlobalSessionVariables(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setGlobalSessionVariables", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setIFX_DIRECTIVES(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setIFX_DIRECTIVES", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setIFX_EXTDIRECTIVES(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setIFX_EXTDIRECTIVES", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setIFX_FLAT_UCSQ(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setIFX_FLAT_UCSQ", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setIFX_UPDDESC(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setIFX_UPDDESC", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setIFX_XASTDCOMPLIANCE_XAEND(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setIFX_XASTDCOMPLIANCE_XAEND", boolean.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setImplicitRollbackOption(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setImplicitRollbackOption", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setIncludeModLevelInProductVersion(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setIncludeModLevelInProductVersion", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setINFORMIXOPCACHE(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setINFORMIXOPCACHE", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setINFORMIXSTACKSIZE(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setINFORMIXSTACKSIZE", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setInterruptProcessingMode(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setInterruptProcessingMode", int.class);
        m.invoke(uDatasource, i);
    }

    public void setIsPrimaryServerUp(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setIsPrimaryServerUp", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setJdbcCollection(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setJdbcCollection", String.class);
        m.invoke(uDatasource, s);
    }

    public void setJdbcCollectionState(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setJdbcCollectionState", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setKeepAliveTimeOut(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setKeepAliveTimeOut", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setKeepDynamic(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setKeepDynamic", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setKerberosServerPrincipal(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setKerberosServerPrincipal", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setkeyUsage(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setkeyUsage", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setLIGHT_SCANS(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setLIGHT_SCANS", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setLKNOTIFY(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setLKNOTIFY", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setLOCKDOWN(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setLOCKDOWN", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setLOCKSSFU(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setLOCKSSFU", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setMaxConnCachedParamBufferSize(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setMaxConnCachedParamBufferSize", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setMaxRetriesForClientReroute(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setMaxRetriesForClientReroute", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setMaxRowsetSize(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setMaxRowsetSize", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setMaxStatements(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setMaxStatements", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setMaxTransportObjects(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setMaxTransportObjects", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setMemberConnectTimeout(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setMemberConnectTimeout", int.class);
        m.invoke(uDatasource, i);
    }

    public void setMonitorCollectionInterval(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setMonitorCollectionInterval", int.class);
        m.invoke(uDatasource, i);
    }

    public void setMonitoredDataSourceName(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setMonitoredDataSourceName", String.class);
        m.invoke(uDatasource, s);
    }

    public void setMonitorEnabled(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setMonitorEnabled", int.class);
        m.invoke(uDatasource, i);
    }

    public void setMonitorLevel(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setMonitorLevel", int.class);
        m.invoke(uDatasource, i);
    }

    public void setMonitorPort(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setMonitorPort", int.class);
        m.invoke(uDatasource, i);
    }

    public void setMonitorServerName(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setMonitorServerName", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setNODEFDAC(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setNODEFDAC", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setNOSHMSG(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setNOSHMSG", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setNOSORTINDEX(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setNOSORTINDEX", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setOPTCOMPIND(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setOPTCOMPIND", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setOptimizationProfile(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setOptimizationProfile", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setOptimizationProfileToFlush(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setOptimizationProfileToFlush", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setOPTOFC(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setOPTOFC", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setPDQPRIORITY(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setPDQPRIORITY", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setPdqProperties(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setPdqProperties", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setPkList(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setPkList", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setPlanName(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setPlanName", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setPLOAD_LO_PATH(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setPLOAD_LO_PATH", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setPluginClassName(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setPluginClassName", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setPluginName(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setPluginName", String.class);
        m.invoke(uDatasource, s);
    }

    public void setProfileName(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setProfileName", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setProgressiveStreaming(int traceLevel) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setProgressiveStreaming", int.class);
        m.invoke(uDatasource, traceLevel);
    }

    public synchronized void setPSORT_DBTEMP(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setPSORT_DBTEMP", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setPSORT_NPROCS(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setPSORT_NPROCS", String.class);
        m.invoke(uDatasource, s);
    }

    public void setQueryAcceleration(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setQueryAcceleration", String.class);
        m.invoke(uDatasource, s);
    }

    public void setQueryAccelerationEnable(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setQueryAccelerationEnable", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setQueryCloseImplicit(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setQueryCloseImplicit", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setQueryDataSize(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setQueryDataSize", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setQueryTimeoutInterruptProcessingMode(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setQueryTimeoutInterruptProcessingMode", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setReadOnly(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setReadOnly", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setRecordTemporalHistory(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setRecordTemporalHistory", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setReportLongTypes(short i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setReportLongTypes", short.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setResultSetHoldability(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setResultSetHoldability", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setResultSetHoldabilityForCatalogQueries(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setResultSetHoldabilityForCatalogQueries", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setRetryIntervalForClientReroute(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setRetryIntervalForClientReroute", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setRetryWithAlternativeSecurityMechanism(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setRetryWithAlternativeSecurityMechanism", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setReturnAlias(short i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setReturnAlias", short.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setSecurityMechanism(short i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSecurityMechanism", short.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setSendCharInputsUTF8(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSendCharInputsUTF8", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setSendDataAsIs(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSendDataAsIs", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setServerBidiStringType(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setServerBidiStringType", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setSessionTimeZone(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSessionTimeZone", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setSLABEL(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSLABEL", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setSORTINDEX(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSORTINDEX", String.class);
        m.invoke(uDatasource, s);
    }

    public void setSpecialRegisters(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSpecialRegisters", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setSQL_FROM_DBIMPORT(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSQL_FROM_DBIMPORT", String.class);
        m.invoke(uDatasource, s);
    }

    public void setSqljAvoidTimeStampConversion(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSqljAvoidTimeStampConversion", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setSqljCloseStmtsWithOpenResultSet(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSqljCloseStmtsWithOpenResultSet", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setSqljEnableClassLoaderSpecificProfiles(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSqljEnableClassLoaderSpecificProfiles", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setSQLSTATS(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSQLSTATS", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setSsid(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSsid", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setSslCertLocation(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSslCertLocation", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setSSLCipherSuites(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSSLCipherSuites", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setSslConnection(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSslConnection", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setSslKeyStoreLocation(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSslKeyStoreLocation", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setSslKeyStorePassword(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSslKeyStorePassword", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setSslKeyStoreType(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSslKeyStoreType", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setSslTrustStoreLocation(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSslTrustStoreLocation", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setSslTrustStorePassword(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSslTrustStorePassword", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setSslTrustStoreType(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSslTrustStoreType", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setSslVersion(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSslVersion", String.class);
        m.invoke(uDatasource, s);
    }

    public void setStatementConcentrator(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setStatementConcentrator", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setSTMT_CACHE(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSTMT_CACHE", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setSTMT_CACHE_DEBUG(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSTMT_CACHE_DEBUG", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setStreamBufferSize(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setStreamBufferSize", int.class);
        m.invoke(uDatasource, i);
    }

    public void setStripTrailingZerosForDecimalNumbers(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setStripTrailingZerosForDecimalNumbers", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setSUBQCACHESZ(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSUBQCACHESZ", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setsupportsAsynchronousXARollback(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setsupportsAsynchronousXARollback", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setSupportsRawDateTimeRetrieval(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSupportsRawDateTimeRetrieval", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setSysSchema(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setSysSchema", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setTimeFormat(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setTimeFormat", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setTimerLevelForQueryTimeOut(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setTimerLevelForQueryTimeOut", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setTimestampFormat(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setTimestampFormat", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setTimestampOutputType(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setTimestampOutputType", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setTimestampPrecisionReporting(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setTimestampPrecisionReporting", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setTraceDirectory(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setTraceDirectory", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setTraceFile(String s) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setTraceFile", String.class);
        m.invoke(uDatasource, s);
    }

    public synchronized void setTraceFileAppend(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setTraceFileAppend", boolean.class);
        m.invoke(uDatasource, b);
    }

    public void setTraceFileCount(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setTraceFileCount", int.class);
        m.invoke(uDatasource, i);
    }

    public void setTraceFileSize(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setTraceFileSize", int.class);
        m.invoke(uDatasource, i);
    }

    public void setTraceOption(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setTraceOption", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setTranslateForBitData(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setTranslateForBitData", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setUpdateCountForBatch(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setUpdateCountForBatch", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setUseCachedCursor(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setUseCachedCursor", boolean.class);
        m.invoke(uDatasource, b);
    }

    public void setUseIdentityValLocalForAutoGeneratedKeys(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setUseIdentityValLocalForAutoGeneratedKeys", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setUseJDBC41DefinitionForGetColumns(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setUseJDBC41DefinitionForGetColumns", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setUseJDBC4ColumnNameAndLabelSemantics(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setUseJDBC4ColumnNameAndLabelSemantics", int.class);
        m.invoke(uDatasource, i);
    }

    public synchronized void setUseRowsetCursor(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setUseRowsetCursor", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setUseTransactionRedirect(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setUseTransactionRedirect", boolean.class);
        m.invoke(uDatasource, b);
    }

    public synchronized void setXaNetworkOptimization(boolean b) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setXaNetworkOptimization", boolean.class);
        m.invoke(uDatasource, b);
    }

    public void setXmlFormat(int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> c = uDatasource.getClass();
        Method m = c.getMethod("setXmlFormat", int.class);
        m.invoke(uDatasource, i);
    }
}
