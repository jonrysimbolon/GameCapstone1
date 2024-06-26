package id.zoneordering.core.utils

import androidx.recyclerview.widget.DiffUtil

class DiffCallback<T>(
    private val mOldUserList: List<T>,
    private val mNewUserList: List<T>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = mOldUserList.size

    override fun getNewListSize(): Int = mNewUserList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        mOldUserList[oldItemPosition] == mNewUserList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = mOldUserList[oldItemPosition]
        val newUser = mNewUserList[newItemPosition]

        return oldUser == newUser && oldUser == newUser
    }

}