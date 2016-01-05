/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/sai/lunwen/workspace/XProtect/src/com/serry/xprotect/service/IPrivacyService.aidl
 */
package com.serry.xprotect.service;
public interface IPrivacyService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.serry.xprotect.service.IPrivacyService
{
private static final java.lang.String DESCRIPTOR = "com.serry.xprotect.service.IPrivacyService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.serry.xprotect.service.IPrivacyService interface,
 * generating a proxy if needed.
 */
public static com.serry.xprotect.service.IPrivacyService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.serry.xprotect.service.IPrivacyService))) {
return ((com.serry.xprotect.service.IPrivacyService)iin);
}
return new com.serry.xprotect.service.IPrivacyService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getVersion:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getVersion();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_check:
{
data.enforceInterface(DESCRIPTOR);
java.util.List _result = this.check();
reply.writeNoException();
reply.writeList(_result);
return true;
}
case TRANSACTION_reportError:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.reportError(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getStatistics:
{
data.enforceInterface(DESCRIPTOR);
java.util.Map _result = this.getStatistics();
reply.writeNoException();
reply.writeMap(_result);
return true;
}
case TRANSACTION_setRestriction:
{
data.enforceInterface(DESCRIPTOR);
com.serry.xprotect.data.PRestriction _arg0;
if ((0!=data.readInt())) {
_arg0 = com.serry.xprotect.data.PRestriction.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.setRestriction(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setRestrictionList:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<com.serry.xprotect.data.PRestriction> _arg0;
_arg0 = data.createTypedArrayList(com.serry.xprotect.data.PRestriction.CREATOR);
this.setRestrictionList(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getRestriction:
{
data.enforceInterface(DESCRIPTOR);
com.serry.xprotect.data.PRestriction _arg0;
if ((0!=data.readInt())) {
_arg0 = com.serry.xprotect.data.PRestriction.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _arg1;
_arg1 = (0!=data.readInt());
java.lang.String _arg2;
_arg2 = data.readString();
com.serry.xprotect.data.PRestriction _result = this.getRestriction(_arg0, _arg1, _arg2);
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_getRestrictionList:
{
data.enforceInterface(DESCRIPTOR);
com.serry.xprotect.data.PRestriction _arg0;
if ((0!=data.readInt())) {
_arg0 = com.serry.xprotect.data.PRestriction.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
java.util.List<com.serry.xprotect.data.PRestriction> _result = this.getRestrictionList(_arg0);
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_isRestrictionSet:
{
data.enforceInterface(DESCRIPTOR);
com.serry.xprotect.data.PRestriction _arg0;
if ((0!=data.readInt())) {
_arg0 = com.serry.xprotect.data.PRestriction.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.isRestrictionSet(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_deleteRestrictions:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
this.deleteRestrictions(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_getUsage:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<com.serry.xprotect.data.PRestriction> _arg0;
_arg0 = data.createTypedArrayList(com.serry.xprotect.data.PRestriction.CREATOR);
long _result = this.getUsage(_arg0);
reply.writeNoException();
reply.writeLong(_result);
return true;
}
case TRANSACTION_getUsageList:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
java.util.List<com.serry.xprotect.data.PRestriction> _result = this.getUsageList(_arg0, _arg1);
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_deleteUsage:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.deleteUsage(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setSetting:
{
data.enforceInterface(DESCRIPTOR);
com.serry.xprotect.data.PSetting _arg0;
if ((0!=data.readInt())) {
_arg0 = com.serry.xprotect.data.PSetting.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.setSetting(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setSettingList:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<com.serry.xprotect.data.PSetting> _arg0;
_arg0 = data.createTypedArrayList(com.serry.xprotect.data.PSetting.CREATOR);
this.setSettingList(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getSetting:
{
data.enforceInterface(DESCRIPTOR);
com.serry.xprotect.data.PSetting _arg0;
if ((0!=data.readInt())) {
_arg0 = com.serry.xprotect.data.PSetting.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.serry.xprotect.data.PSetting _result = this.getSetting(_arg0);
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_getSettingList:
{
data.enforceInterface(DESCRIPTOR);
com.serry.xprotect.data.PSetting _arg0;
if ((0!=data.readInt())) {
_arg0 = com.serry.xprotect.data.PSetting.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
java.util.List<com.serry.xprotect.data.PSetting> _result = this.getSettingList(_arg0);
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_deleteSettings:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.deleteSettings(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_clear:
{
data.enforceInterface(DESCRIPTOR);
this.clear();
reply.writeNoException();
return true;
}
case TRANSACTION_flush:
{
data.enforceInterface(DESCRIPTOR);
this.flush();
reply.writeNoException();
return true;
}
case TRANSACTION_dump:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.dump(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.serry.xprotect.service.IPrivacyService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public int getVersion() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getVersion, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List check() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_check, _data, _reply, 0);
_reply.readException();
java.lang.ClassLoader cl = (java.lang.ClassLoader)this.getClass().getClassLoader();
_result = _reply.readArrayList(cl);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void reportError(java.lang.String message) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(message);
mRemote.transact(Stub.TRANSACTION_reportError, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.util.Map getStatistics() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.Map _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getStatistics, _data, _reply, 0);
_reply.readException();
java.lang.ClassLoader cl = (java.lang.ClassLoader)this.getClass().getClassLoader();
_result = _reply.readHashMap(cl);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setRestriction(com.serry.xprotect.data.PRestriction restriction) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((restriction!=null)) {
_data.writeInt(1);
restriction.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setRestriction, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setRestrictionList(java.util.List<com.serry.xprotect.data.PRestriction> listRestriction) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeTypedList(listRestriction);
mRemote.transact(Stub.TRANSACTION_setRestrictionList, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public com.serry.xprotect.data.PRestriction getRestriction(com.serry.xprotect.data.PRestriction restriction, boolean usage, java.lang.String secret) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.serry.xprotect.data.PRestriction _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((restriction!=null)) {
_data.writeInt(1);
restriction.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(((usage)?(1):(0)));
_data.writeString(secret);
mRemote.transact(Stub.TRANSACTION_getRestriction, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.serry.xprotect.data.PRestriction.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<com.serry.xprotect.data.PRestriction> getRestrictionList(com.serry.xprotect.data.PRestriction selector) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<com.serry.xprotect.data.PRestriction> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((selector!=null)) {
_data.writeInt(1);
selector.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_getRestrictionList, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(com.serry.xprotect.data.PRestriction.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isRestrictionSet(com.serry.xprotect.data.PRestriction restriction) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((restriction!=null)) {
_data.writeInt(1);
restriction.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_isRestrictionSet, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void deleteRestrictions(int uid, java.lang.String restrictionName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(uid);
_data.writeString(restrictionName);
mRemote.transact(Stub.TRANSACTION_deleteRestrictions, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public long getUsage(java.util.List<com.serry.xprotect.data.PRestriction> restriction) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeTypedList(restriction);
mRemote.transact(Stub.TRANSACTION_getUsage, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<com.serry.xprotect.data.PRestriction> getUsageList(int uid, java.lang.String restrictionName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<com.serry.xprotect.data.PRestriction> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(uid);
_data.writeString(restrictionName);
mRemote.transact(Stub.TRANSACTION_getUsageList, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(com.serry.xprotect.data.PRestriction.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void deleteUsage(int uid) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(uid);
mRemote.transact(Stub.TRANSACTION_deleteUsage, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setSetting(com.serry.xprotect.data.PSetting setting) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((setting!=null)) {
_data.writeInt(1);
setting.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setSetting, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setSettingList(java.util.List<com.serry.xprotect.data.PSetting> listSetting) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeTypedList(listSetting);
mRemote.transact(Stub.TRANSACTION_setSettingList, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public com.serry.xprotect.data.PSetting getSetting(com.serry.xprotect.data.PSetting setting) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.serry.xprotect.data.PSetting _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((setting!=null)) {
_data.writeInt(1);
setting.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_getSetting, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.serry.xprotect.data.PSetting.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<com.serry.xprotect.data.PSetting> getSettingList(com.serry.xprotect.data.PSetting selector) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<com.serry.xprotect.data.PSetting> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((selector!=null)) {
_data.writeInt(1);
selector.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_getSettingList, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(com.serry.xprotect.data.PSetting.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void deleteSettings(int uid) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(uid);
mRemote.transact(Stub.TRANSACTION_deleteSettings, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void clear() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_clear, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void flush() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_flush, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void dump(int uid) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(uid);
mRemote.transact(Stub.TRANSACTION_dump, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_getVersion = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_check = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_reportError = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_getStatistics = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_setRestriction = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_setRestrictionList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_getRestriction = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_getRestrictionList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_isRestrictionSet = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_deleteRestrictions = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_getUsage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_getUsageList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_deleteUsage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_setSetting = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_setSettingList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_getSetting = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_getSettingList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_deleteSettings = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_clear = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_flush = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_dump = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
}
public int getVersion() throws android.os.RemoteException;
public java.util.List check() throws android.os.RemoteException;
public void reportError(java.lang.String message) throws android.os.RemoteException;
public java.util.Map getStatistics() throws android.os.RemoteException;
public void setRestriction(com.serry.xprotect.data.PRestriction restriction) throws android.os.RemoteException;
public void setRestrictionList(java.util.List<com.serry.xprotect.data.PRestriction> listRestriction) throws android.os.RemoteException;
public com.serry.xprotect.data.PRestriction getRestriction(com.serry.xprotect.data.PRestriction restriction, boolean usage, java.lang.String secret) throws android.os.RemoteException;
public java.util.List<com.serry.xprotect.data.PRestriction> getRestrictionList(com.serry.xprotect.data.PRestriction selector) throws android.os.RemoteException;
public boolean isRestrictionSet(com.serry.xprotect.data.PRestriction restriction) throws android.os.RemoteException;
public void deleteRestrictions(int uid, java.lang.String restrictionName) throws android.os.RemoteException;
public long getUsage(java.util.List<com.serry.xprotect.data.PRestriction> restriction) throws android.os.RemoteException;
public java.util.List<com.serry.xprotect.data.PRestriction> getUsageList(int uid, java.lang.String restrictionName) throws android.os.RemoteException;
public void deleteUsage(int uid) throws android.os.RemoteException;
public void setSetting(com.serry.xprotect.data.PSetting setting) throws android.os.RemoteException;
public void setSettingList(java.util.List<com.serry.xprotect.data.PSetting> listSetting) throws android.os.RemoteException;
public com.serry.xprotect.data.PSetting getSetting(com.serry.xprotect.data.PSetting setting) throws android.os.RemoteException;
public java.util.List<com.serry.xprotect.data.PSetting> getSettingList(com.serry.xprotect.data.PSetting selector) throws android.os.RemoteException;
public void deleteSettings(int uid) throws android.os.RemoteException;
public void clear() throws android.os.RemoteException;
public void flush() throws android.os.RemoteException;
public void dump(int uid) throws android.os.RemoteException;
}
